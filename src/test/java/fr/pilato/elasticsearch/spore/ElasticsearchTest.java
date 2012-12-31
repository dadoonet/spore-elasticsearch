package fr.pilato.elasticsearch.spore;
/*
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.ImmutableMap;
import junit.framework.Assert;
import net.linkfluence.jspore.Spore;
import net.linkfluence.jspore.SporeException;
import net.linkfluence.jspore.SporeResult;
import org.elasticsearch.common.io.FileSystemUtils;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.junit.Assert.*;

/**
 * Test the elasticsearch spore spec.
 * @author David Pilato <david@pilato.fr>
 */
public class ElasticsearchTest {

    protected static final String _index = "sporeidx";
    protected static final String _type = "sporetype";
    protected static final String _id = "myid1";
    
    private static Spore<JsonNode> spore;
    private static Spore<String> sporeString;
    private static Node node;
    
	private static void removeOldDataDir() throws Exception {
		Settings settings = ImmutableSettings.settingsBuilder().loadFromClasspath("elasticsearch.yml").build();

		// First we delete old datas...
		File dataDir = new File(settings.get("path.data"));
		if (dataDir.exists()) {
			FileSystemUtils.deleteRecursively(dataDir, true);
		}
	}

    @SuppressWarnings("unchecked")
	@BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	URL url = ElasticsearchTest.class.getResource("/elasticsearch-spore-0.20.2.json");
        File spec = new File(url.getFile());

		if (node == null) {
			// We remove old data before launching tests
			removeOldDataDir();
			
			// Then we start our node for tests
			node = NodeBuilder.nodeBuilder().node();

			// We wait now for the yellow (or green) status
			node.client().admin().cluster().prepareHealth()
					.setWaitForYellowStatus().execute().actionGet();

			Assert.assertNotNull(node);
			Assert.assertFalse(node.isClosed());
		}
        
        spore = new Spore.Builder<JsonNode>()
                .addMiddleware(Spore.JSON)
                .addSpecContent(spec)
                .setBaseUrl("http://localhost:9200")
                .setDebug(true)
                .build();
        sporeString = new Spore.Builder<String>()
                .addMiddleware(Spore.JSON)
                .addSpecContent(spec)
                .setBaseUrl("http://localhost:9200")
                .setDebug(true)
                .build();
    }
    
    @AfterClass
    public static void tearDown() {
    	if (node != null) node.close();
    }
    
    @Before
    public void setup() {
    	// We clean test index if exists
    	try {
        	node.client().admin().indices().prepareDelete(_index).execute().actionGet();
			// We wait now for the yellow (or green) status
			node.client().admin().cluster().prepareHealth()
					.setWaitForYellowStatus().execute().actionGet();
    	} catch (Exception e) {
    		// We do nothing here
    	}
    }
    
    @Test
    public void test_welcome() throws SporeException {
        SporeResult<JsonNode> result = spore.call("welcome");
        assertTrue(result.body.get("ok").asBoolean());
    }
    
    @Test
    public void test_create_index() throws SporeException {
        SporeResult<JsonNode> result = spore.call("create_index", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build());
        assertTrue(result.body.get("ok").asBoolean());
    }
    
    @Test // TODO Fix it, although it works in CURL 
    // $ curl -XPUT http://localhost:9200/sporeidx/sporetype/_mapping -d '{"sporetype":{"properties":{}}}'
    // {"ok":true,"acknowledged":true}
    public void test_put_mapping() throws SporeException, IOException {
    	// We need to create an index first
    	node.client().admin().indices().prepareCreate(_index).execute().actionGet();
    	
    	XContentBuilder xb = jsonBuilder()
				.startObject()
					.startObject(_type)
						.startObject("properties")
						.endObject()
					.endObject()
				.endObject();
        SporeResult<JsonNode> result = spore.call("put_mapping", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .build(),
                xb.string());
        assertNotNull(result.body.get("ok"));
        assertTrue(result.body.get("ok").asBoolean());
    }

    @Test
    public void test_get_mapping() throws SporeException, IOException {
    	// We create a doc
    	node.client().prepareIndex(_index, _type).setSource("{\"myprop\":\"value\"}").setRefresh(true).execute().actionGet();
    	
    	waitForCluster();
    	
    	// We test to get a specific mapping
    	SporeResult<JsonNode> result = spore.call("get_mapping", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .build());
        assertNotNull(result.body.get(_type));

        // We want all mappings for a given index
    	result = spore.call("get_mapping", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build());
        assertNotNull(result.body.get(_index));

        // We want all mappings for the whole cluster
    	result = spore.call("get_mapping");
        assertNotNull(result.body.get(_index));
    }

    @Test
    public void test_index() throws SporeException, IOException {
    	SporeResult<JsonNode> result = spore.call("index", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .build(),
                BeerHelper.generate());
        assertTrue(result.body.get("ok").asBoolean());

        // We want all mappings for a given index
    	result = spore.call("index", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .put("id", _id)
                .build(),
                BeerHelper.generate());
        assertTrue(result.body.get("ok").asBoolean());
    }

    @Test
    public void test_search() throws SporeException, IOException {
    	// We inject some beans
    	test_index();
    	
    	node.client().admin().indices().prepareRefresh(_index).execute().actionGet();
    	SporeResult<JsonNode> result = spore.call("search", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .build(),
                "{\"query\":{\"match_all\":{}}}");
        assertNotNull(result.body.get("took"));
        assertEquals(2, result.body.get("hits").get("total").asInt());

        result = spore.call("search", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build(),
                "{\"query\":{\"match_all\":{}}}");
        assertNotNull(result.body.get("took"));
        assertEquals(2, result.body.get("hits").get("total").asInt());

        result = spore.call("search", "{\"query\":{\"match_all\":{}}}");
        assertNotNull(result.body.get("took"));
        assertEquals(2, result.body.get("hits").get("total").asInt());
    }

    /**
     * We expect to have here a 404 not found Exception
     * @throws SporeException
     * @throws IOException
     */
    @Test
    public void test_delete() throws SporeException, IOException {
        SporeResult<JsonNode> result = null;
        result = spore.call("delete", new ImmutableMap.Builder<String, String>()
            .put("index", _index)
            .put("type", _type)
            .put("id", "doesnoexistid")
            .build());
        assertTrue(result.body.get("ok").asBoolean());
        assertEquals(404, result.response.getStatusCode());
    }

    @Test
    public void test_delete_mapping() throws SporeException, IOException {
    	// We inject some beans
    	test_index();
    	
    	node.client().admin().indices().prepareRefresh(_index).execute().actionGet();
    	SporeResult<JsonNode> result = spore.call("delete_mapping", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .build());
        assertTrue(result.body.get("ok").asBoolean());
    }
    
    @Test
    public void test_delete_index() throws SporeException, IOException {
    	// We inject some beans
    	test_index();
    	
    	node.client().admin().indices().prepareRefresh(_index).execute().actionGet();
    	SporeResult<JsonNode> result = spore.call("delete_index", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build());
        assertTrue(result.body.get("ok").asBoolean());
    }

    @Test
    public void test_delete_index_all() throws SporeException, IOException {
    	// We inject some beans
    	test_index();
    	
    	node.client().admin().indices().prepareRefresh(_index).execute().actionGet();
    	SporeResult<JsonNode> result = spore.call("delete_index", new ImmutableMap.Builder<String, String>()
                .put("index", "_all")
                .build());
        assertTrue(result.body.get("ok").asBoolean());
    }

    @Test
    public void test_analyze() throws SporeException, IOException {
    	SporeResult<JsonNode> result = spore.call("analyze", "Elasticsearch is cool");
    	Object tokens = result.body.get("tokens");
    	
    	assertNotNull(tokens);
    	
    	if (tokens instanceof ArrayNode) {
			ArrayNode _tokens = (ArrayNode) tokens;
	    	assertEquals(2, _tokens.size());
		} else {
			fail("tokens should be an array");
		}
    }

    /**
     * Testing multi search API. But should works!
     * @throws SporeException
     * @throws IOException
     */
    @Test
    // TODO Fix it : wait for PR https://github.com/AsyncHttpClient/async-http-client/pull/193
    // and update in JSpore for async http client 1.8.0
    // and find why in ES the following document does not work as is
    public void test_multi_search() throws SporeException, IOException {
    	// We inject some beans
    	test_index();
    	
    	node.client().admin().indices().prepareRefresh(_index).execute().actionGet();
    	SporeResult<JsonNode> result = spore.call("multi_search", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .build(),
                "{}" +
                "{\"query\":{\"match_all\":{}}}" +
                "");
        assertNotNull(result.body.get("took"));

        result = spore.call("multi_search", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build(),
                "{\"query\":{\"match_all\":{}}}");
        assertNotNull(result.body.get("took"));
        
        result = spore.call("multi_search", "{\"query\":{\"match_all\":{}}}");
        assertNotNull(result.body.get("took"));    
    }

    @Test
    public void test_count() throws SporeException, IOException, InterruptedException {
    	// We inject some beans
    	test_index();
    	
    	node.client().admin().indices().prepareRefresh().execute().actionGet();

    	SporeResult<JsonNode> result = spore.call("count", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .build(),
                "{\"match_all\":{}}");
        assertEquals(2, result.body.get("count").asInt());

        result = spore.call("count", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build(),
                "{\"match_all\":{}}");
        assertEquals(2, result.body.get("count").asInt());
        
        result = spore.call("count", "{\"match_all\":{}}");
        assertEquals(2, result.body.get("count").asInt());
    }

    @Test
    public void test_status() throws SporeException, IOException {
    	// We inject some beans
    	test_index();
    	
    	node.client().admin().indices().prepareRefresh(_index).execute().actionGet();
    	SporeResult<JsonNode> result = spore.call("status", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build());
        assertTrue(result.body.get("ok").asBoolean());

        result = spore.call("status");
        assertTrue(result.body.get("ok").asBoolean());
    }  
    
    @Test
    public void test_shutdown() throws SporeException, IOException {
    	SporeResult<JsonNode> result = spore.call("shutdown", new ImmutableMap.Builder<String, String>()
                .put("nodes", "nonexistingnode")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());
    }    
    
    @Test
    public void test_nodes_stats() throws SporeException, IOException {
    	SporeResult<JsonNode> result = spore.call("nodes_stats", new ImmutableMap.Builder<String, String>()
                .put("nodes", "_all")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());

        result = spore.call("nodes_stats", new ImmutableMap.Builder<String, String>()
                .put("nodes", "_all")
                .put("stat", "jvm")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());

        result = spore.call("nodes_stats", new ImmutableMap.Builder<String, String>()
                .put("stat", "fs")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());
        result = spore.call("nodes_stats", new ImmutableMap.Builder<String, String>()
                .put("stat", "http")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());
        result = spore.call("nodes_stats", new ImmutableMap.Builder<String, String>()
                .put("stat", "indices")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());
        result = spore.call("nodes_stats", new ImmutableMap.Builder<String, String>()
                .put("stat", "jvm")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());
        result = spore.call("nodes_stats", new ImmutableMap.Builder<String, String>()
                .put("stat", "network")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());
        result = spore.call("nodes_stats", new ImmutableMap.Builder<String, String>()
                .put("stat", "os")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());
        result = spore.call("nodes_stats", new ImmutableMap.Builder<String, String>()
                .put("stat", "process")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());
        result = spore.call("nodes_stats", new ImmutableMap.Builder<String, String>()
                .put("stat", "thread_pool")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());
        result = spore.call("nodes_stats", new ImmutableMap.Builder<String, String>()
                .put("stat", "transport")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());

        result = spore.call("nodes_stats");
        assertEquals("es_spore", result.body.get("cluster_name").asText());

    }

    @Test
    public void test_nodes_info() throws SporeException, IOException {
        SporeResult<JsonNode> result = spore.call("nodes_info");
        assertNotNull(result.body.get("nodes"));

        result = spore.call("nodes_info", new ImmutableMap.Builder<String, String>()
                .put("nodes", "_all")
                .build());
        assertNotNull(result.body.get("nodes"));

        result = spore.call("nodes_info", new ImmutableMap.Builder<String, String>()
                .put("nodes", "_all")
                .put("info", "http")
                .build());
        assertNotNull(result.body.get("nodes").elements().next().get("http"));

        result = spore.call("nodes_info", new ImmutableMap.Builder<String, String>()
                .put("info", "jvm")
                .build());
        assertNotNull(result.body.get("nodes").elements().next().get("jvm"));

        result = spore.call("nodes_info", new ImmutableMap.Builder<String, String>()
                .put("info", "network")
                .build());
        assertNotNull(result.body.get("nodes").elements().next().get("network"));

        result = spore.call("nodes_info", new ImmutableMap.Builder<String, String>()
                .put("info", "os")
                .build());
        assertNotNull(result.body.get("nodes").elements().next().get("os"));

        result = spore.call("nodes_info", new ImmutableMap.Builder<String, String>()
                .put("info", "process")
                .build());
        assertNotNull(result.body.get("nodes").elements().next().get("process"));

        result = spore.call("nodes_info", new ImmutableMap.Builder<String, String>()
                .put("info", "settings")
                .build());
        assertNotNull(result.body.get("nodes").elements().next().get("settings"));

        result = spore.call("nodes_info", new ImmutableMap.Builder<String, String>()
                .put("info", "thread_pool")
                .build());
        assertNotNull(result.body.get("nodes").elements().next().get("thread_pool"));

        result = spore.call("nodes_info", new ImmutableMap.Builder<String, String>()
                .put("info", "transport")
                .build());
        assertNotNull(result.body.get("nodes").elements().next().get("transport"));

    }

    /**
     * For this test, we get back a String and not a valid JSon.
     * <br/>We only expect not to have any exception
     * @throws SporeException
     * @throws IOException
     */
    @Test
    public void test_hot_threads() throws SporeException, IOException {
    	SporeResult<JsonNode> result = spore.call("hot_threads", new ImmutableMap.Builder<String, String>()
                .put("nodes", "_all")
                .build());
    }
    
    @Test
    public void test_cluster_state() throws SporeException, IOException {
    	SporeResult<JsonNode> result = spore.call("cluster_state");
        assertNotNull(result.body.get("master_node").asText());
    }  
    
    @Test
    public void test_cluster_health() throws SporeException, IOException {
    	SporeResult<JsonNode> result = spore.call("cluster_health");
        assertEquals("es_spore", result.body.get("cluster_name").asText());

        // Index some docs
        test_index();
        
    	result = spore.call("cluster_health", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());
    }  
    
    @Test
    public void test_cluster_settings() throws SporeException, IOException {
    	SporeResult<JsonNode> result = spore.call("cluster_settings");
        assertNotNull(result.body.get("transient").asText());
    }

    @Test
    // TODO Wait for JSpore Release see PR : https://github.com/nicoo/jspore/pull/2
    public void test_put_cluster_settings() throws SporeException, IOException {
    	SporeResult<String> result = sporeString.call("put_cluster_settings", "{\"transient\":{\"indices.ttl.interval\":\"60s\"}}");
        assertTrue(result.body.equals(""));
    }

    @Test
    public void test_flush() throws SporeException, IOException {
        // Index some docs
        test_index();

        SporeResult<JsonNode> result = spore.call("flush");
        assertTrue(result.body.get("ok").asBoolean());

        result = spore.call("flush", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build());
        assertTrue(result.body.get("ok").asBoolean());
    }

    @Test
    public void test_index_settings() throws SporeException, IOException {
        // We inject some beans
        test_index();
        node.client().admin().indices().prepareRefresh(_index).execute().actionGet();

        SporeResult<JsonNode> result = spore.call("index_settings");
        assertNotNull(result.body.get(_index).get("settings"));

        result = spore.call("index_settings", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build());
        assertNotNull(result.body.get(_index).get("settings"));
    }

    @Test
    public void test_index_put_settings() throws SporeException, IOException {
        // We inject some beans
        test_index();
        node.client().admin().indices().prepareRefresh(_index).execute().actionGet();

        SporeResult<JsonNode> result = spore.call("index_put_settings",
                "{\"index\" : {\"number_of_replicas\" : 4}}");
        assertTrue(result.body.get("ok").asBoolean());

        result = spore.call("index_put_settings", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build(),
                "{\"index\" : {\"number_of_replicas\" : 1}}");
        assertTrue(result.body.get("ok").asBoolean());
    }

    @Test
    public void test_index_stats() throws SporeException, IOException {
        // We inject some beans
        test_index();
        node.client().admin().indices().prepareRefresh(_index).execute().actionGet();

        SporeResult<JsonNode> result = spore.call("index_stats");
        assertEquals(2, result.body.get("_all").get("settings").get("docs").get("count").asInt());

        result = spore.call("index_stats", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build());
        assertEquals(2, result.body.get("_all").get("primaries").get("docs").get("count").asInt());

        result = spore.call("index_stats", new ImmutableMap.Builder<String, String>()
                .put("index", "_all")
                .build());
        assertEquals(2, result.body.get("_all").get("primaries").get("docs").get("count").asInt());

        indexStatTestHelper("docs", "store");
        indexStatTestHelper("store", "docs");
        indexStatTestHelper("indexing", "docs");
        indexStatTestHelper("get", "docs");
        indexStatTestHelper("search", "docs");
        indexStatTestHelper("indexing", "store", _type);
        indexStatTestHelper("warmer", "docs");
    }

    private void indexStatTestHelper(String expected, String notexpected, String... types) throws SporeException {
        ImmutableMap.Builder<String, String> parameters = new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("option", expected);

        String type = "";
        for (int i = 0; i < types.length; i++) {
            if (i > 0) type += ",";
            type += types[i];
        }

        parameters.put("type", type);

        SporeResult<JsonNode> result = spore.call("index_stats", parameters.build());
        assertNull(result.body.get("_all").get("primaries").get(notexpected));
        assertNotNull(result.body.get("_all").get("primaries").get(expected));
    }

    @Test
    public void test_index_stats_options() throws SporeException, IOException {
        // We inject some beans
        test_index();
        node.client().admin().indices().prepareRefresh(_index).execute().actionGet();

        SporeResult<JsonNode> result = spore.call("index_stats_options", new ImmutableMap.Builder<String, String>()
                .put("index", "_all")
                .put("flush", "false")
                .put("merge", "false")
                .put("refresh", "false")
                .build());
        assertNull(result.body.get("_all").get("primaries").get("refresh"));
        assertNull(result.body.get("_all").get("primaries").get("merges"));
        assertNull(result.body.get("_all").get("primaries").get("flush"));

        result = spore.call("index_stats_options", new ImmutableMap.Builder<String, String>()
                .put("index", "_all")
                .put("flush", "true")
                .put("merge", "true")
                .put("refresh", "true")
                .build());
        assertNotNull(result.body.get("_all").get("primaries").get("refresh"));
        assertNotNull(result.body.get("_all").get("primaries").get("merges"));
        assertNotNull(result.body.get("_all").get("primaries").get("flush"));
    }

    private void waitForCluster() {
    	// TODO : change that : We wait for 500 ms
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
    }
}
