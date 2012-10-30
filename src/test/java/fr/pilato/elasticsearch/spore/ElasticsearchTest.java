package fr.pilato.elasticsearch.spore;
/*
 */

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import junit.framework.Assert;
import net.linkfluence.jspore.Spore;
import net.linkfluence.jspore.SporeException;
import net.linkfluence.jspore.SporeResult;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.elasticsearch.common.io.FileSystemUtils;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Test the elasticsearch spore spec.
 * @author David Pilato <david@pilato.fr>
 */
public class ElasticsearchTest {

    protected static final String _index = "sporeidx";
    protected static final String _type = "sporetype";
    protected static final String _id = "myid1";
    
    private static Spore<JsonNode> spore;
    private static Node node;
    
	private static void removeOldDataDir() throws Exception {
		Settings settings = ImmutableSettings.settingsBuilder().loadFromClasspath("elasticsearch.yml").build();

		// First we delete old datas...
		File dataDir = new File(settings.get("path.data"));
		if (dataDir.exists()) {
			FileSystemUtils.deleteRecursively(dataDir, true);
		}
	}

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	URL url = ElasticsearchTest.class.getResource("/elasticsearch-spore-0.20.0.json");
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
    
    @Test
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
    	// TODO : change that : We wait for 500 ms
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
    	
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
    	// TODO : change that : We wait for 500 ms
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

    	// We inject some beans
    	test_index();
    	
    	node.client().admin().indices().prepareRefresh(_index).execute().actionGet();
    	SporeResult<JsonNode> result = spore.call("search", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .build(),
                "{\"query\":{\"match_all\":{}}}");
        assertNotNull(result.body.get("took"));

        result = spore.call("search", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build(),
                "{\"query\":{\"match_all\":{}}}");
        assertNotNull(result.body.get("took"));
        
        result = spore.call("search", "{\"query\":{\"match_all\":{}}}");
        assertNotNull(result.body.get("took"));    
    }

    @Test
    public void test_delete() throws SporeException, IOException {
    	SporeResult<JsonNode> result = spore.call("delete", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .put("id", "doesnoexistid")
                .build());
        assertTrue(result.body.get("ok").asBoolean());
    }

    @Test
    public void test_delete_mapping() throws SporeException, IOException {
    	// TODO : change that : We wait for 500 ms
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

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
    	// TODO : change that : We wait for 500 ms
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

    	// We inject some beans
    	test_index();
    	
    	node.client().admin().indices().prepareRefresh(_index).execute().actionGet();
    	SporeResult<JsonNode> result = spore.call("delete_index", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build());
        assertTrue(result.body.get("ok").asBoolean());
    }

    @Test
    public void test_analyze() throws SporeException, IOException {
    	// TODO : change that : We wait for 500 ms
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

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

    @Test @Ignore
    public void test_multi_search() throws SporeException, IOException {
    	// TODO : change that : We wait for 500 ms
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

    	// We inject some beans
    	test_index();
    	
    	node.client().admin().indices().prepareRefresh(_index).execute().actionGet();
    	SporeResult<JsonNode> result = spore.call("multi_search", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .build(),
                "{}\n{\"query\":{\"match_all\":{}}}\n");
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
    public void test_count() throws SporeException, IOException {
    	// TODO : change that : We wait for 500 ms
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

    	// We inject some beans
    	test_index();
    	
    	node.client().admin().indices().prepareRefresh(_index).execute().actionGet();
    	SporeResult<JsonNode> result = spore.call("count", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .put("type", _type)
                .build(),
                "{\"match_all\":{}}");
        assertNotNull(result.body.get("took"));

        result = spore.call("count", new ImmutableMap.Builder<String, String>()
                .put("index", _index)
                .build(),
                "{\"match_all\":{}}");
        assertNotNull(result.body.get("took"));
        
        result = spore.call("count", "{\"match_all\":{}}");
        assertNotNull(result.body.get("took"));    
    }

    @Test
    public void test_status() throws SporeException, IOException {
    	// TODO : change that : We wait for 500 ms
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

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
    	// TODO : change that : We wait for 500 ms
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

    	SporeResult<JsonNode> result = spore.call("shutdown", new ImmutableMap.Builder<String, String>()
                .put("nodes", "nonexistingnode")
                .build());
    }    
    
    @Test
    public void test_nodes_stats() throws SporeException, IOException {
    	// TODO : change that : We wait for 500 ms
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

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
                .put("stat", "jvm")
                .build());
        assertEquals("es_spore", result.body.get("cluster_name").asText());

        result = spore.call("nodes_stats");
        assertEquals("es_spore", result.body.get("cluster_name").asText());

    }  
    
    
}
