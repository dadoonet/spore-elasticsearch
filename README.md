SPORE Specifications for Elasticsearch
======================================

Welcome to the [Elasticsearch](http://www.elasticsearch.org/) [SPORE](https://github.com/SPORE/specifications) project

Versions
--------

| spore-elasticsearch  | ElasticSearch |
|:--------------------:|:-------------:|
|   master (0.0.1)     |    0.20.0.RC1 |

Build Status
------------

Thanks to cloudbees for the [build status](https://buildhive.cloudbees.com): [![Build Status](https://buildhive.cloudbees.com/job/dadoonet/job/spore-elasticsearch/badge/icon)](https://buildhive.cloudbees.com/job/dadoonet/job/spore-elasticsearch/)

Available APIs
--------------

| API Name             |    Method     |               URL                     |                                Documentation                                      | 
|----------------------|:-------------:|---------------------------------------|-----------------------------------------------------------------------------------|
| welcome              |     GET       | /                                     |                                                                                   |
| create_index         |     PUT       | /{index}                              |http://www.elasticsearch.org/guide/reference/api/admin-indices-create-index.html   |
| delete_index         |    DELETE     | /{index}                              |http://www.elasticsearch.org/guide/reference/api/admin-indices-delete-index.html   |
| put_mapping          |     PUT       | /{index}/_mapping                     |http://www.elasticsearch.org/guide/reference/api/admin-indices-put-mapping.html    |
| get_mapping          |     GET       | /{index}/{type}/_mapping              |http://www.elasticsearch.org/guide/reference/api/admin-indices-get-mapping.html    |
| delete_mapping       |    DELETE     | /{index}/{type}                       |http://www.elasticsearch.org/guide/reference/api/admin-indices-delete-mapping.html |
| index                |     POST      | /{index}/{type}/{id}                  |http://www.elasticsearch.org/guide/reference/api/index_.html                       |
| search               |     POST      | /{index}/{type}/_search               |http://www.elasticsearch.org/guide/reference/api/search/                           |
| delete               |    DELETE     | /{index}/{type}/{id}                  |http://www.elasticsearch.org/guide/reference/api/delete.html                       |
| analyze              |     GET       | /_analyze                             |http://www.elasticsearch.org/guide/reference/api/admin-indices-analyze.html        |
| count                |     POST      | /_count                               |http://www.elasticsearch.org/guide/reference/api/count.html                        |
| status               |     GET       | /{index}/_status                      |http://www.elasticsearch.org/guide/reference/api/admin-indices-status.html         |
| shutdown             |     POST      | /_cluster/nodes/{nodes}/_shutdown     |http://www.elasticsearch.org/guide/reference/api/admin-cluster-nodes-shutdown.html |
|                      |               |                                       |                                                                                   |
|                      |               |                                       |                                                                                   |


TODO
----

Thanks to @imotov, it's easy to get all REST entry points:

```sh
ES_DIR=.
grep "controller.registerHandler\(([^,]*),[^,]*" $ES_DIR -o -h -E -R --include "*.java" | \
  sed s/controller\.registerHandler\(// | sed s/RestRequest.Method.// | \
  sort -t, -k1 -k2 > methods.txt
```

Here are the methods that have to be implemented in SPORE:

| API Name             |    Method     |               URL                     |                                Documentation                                      | 
|----------------------|:-------------:|---------------------------------------|-----------------------------------------------------------------------------------|
| multi_search         |     POST      | /{index}/{type}/_msearch              |http://www.elasticsearch.org/guide/reference/api/multi-search.html                 |


```
DELETE, "/"
DELETE, "/_template/{name}"
DELETE, "/{index}/_query"
DELETE, "/{index}/_warmer"
DELETE, "/{index}/_warmer/{name}"
DELETE, "/{index}/{type}/_query"
DELETE, "/{index}/{type}/_warmer/{name}"
GET, "/_aliases"
GET, "/_cache/clear"
GET, "/_cluster/health/{index}"
GET, "/_cluster/nodes/{nodeId}"
GET, "/_cluster/nodes/{nodeId}/hot_threads"
GET, "/_cluster/settings"
GET, "/_cluster/state"
GET, "/_flush"
GET, "/_mget"
GET, "/_nodes"
GET, "/_nodes/fs/stats"
GET, "/_nodes/hot_threads"
GET, "/_nodes/hotthreads"
GET, "/_nodes/http"
GET, "/_nodes/http/stats"
GET, "/_nodes/indices/stats"
GET, "/_nodes/jvm"
GET, "/_nodes/jvm/stats"
GET, "/_nodes/network"
GET, "/_nodes/network/stats"
GET, "/_nodes/os"
GET, "/_nodes/os/stats"
GET, "/_nodes/process"
GET, "/_nodes/process/stats"
GET, "/_nodes/settings"
GET, "/_nodes/stats"
GET, "/_nodes/stats/fs"
GET, "/_nodes/stats/http"
GET, "/_nodes/stats/indices"
GET, "/_nodes/stats/jvm"
GET, "/_nodes/stats/network"
GET, "/_nodes/stats/os"
GET, "/_nodes/stats/process"
GET, "/_nodes/stats/thread_pool"
GET, "/_nodes/stats/transport"
GET, "/_nodes/thread_pool"
GET, "/_nodes/thread_pool/stats"
GET, "/_nodes/transport"
GET, "/_nodes/transport/stats"
GET, "/_nodes/{nodeId}"
GET, "/_nodes/{nodeId}/fs/stats"
GET, "/_nodes/{nodeId}/hot_threads"
GET, "/_nodes/{nodeId}/hotthreads"
GET, "/_nodes/{nodeId}/http"
GET, "/_nodes/{nodeId}/http/stats"
GET, "/_nodes/{nodeId}/indices/stats"
GET, "/_nodes/{nodeId}/jvm"
GET, "/_nodes/{nodeId}/jvm/stats"
GET, "/_nodes/{nodeId}/network"
GET, "/_nodes/{nodeId}/network/stats"
GET, "/_nodes/{nodeId}/os"
GET, "/_nodes/{nodeId}/os/stats"
GET, "/_nodes/{nodeId}/process"
GET, "/_nodes/{nodeId}/process/stats"
GET, "/_nodes/{nodeId}/settings"
GET, "/_nodes/{nodeId}/stats"
GET, "/_nodes/{nodeId}/stats/fs"
GET, "/_nodes/{nodeId}/stats/http"
GET, "/_nodes/{nodeId}/stats/indices"
GET, "/_nodes/{nodeId}/stats/jvm"
GET, "/_nodes/{nodeId}/stats/network"
GET, "/_nodes/{nodeId}/stats/os"
GET, "/_nodes/{nodeId}/stats/process"
GET, "/_nodes/{nodeId}/stats/thread_pool"
GET, "/_nodes/{nodeId}/stats/transport"
GET, "/_nodes/{nodeId}/thread_pool"
GET, "/_nodes/{nodeId}/thread_pool/stats"
GET, "/_nodes/{nodeId}/transport"
GET, "/_nodes/{nodeId}/transport/stats"
GET, "/_optimize"
GET, "/_refresh"
GET, "/_search/scroll"
GET, "/_search/scroll/{scroll_id}"
GET, "/_segments"
GET, "/_settings"
GET, "/_stats"
GET, "/_stats/flush"
GET, "/_stats/get"
GET, "/_stats/indexing"
GET, "/_stats/indexing/{indexingTypes1}"
GET, "/_stats/merge"
GET, "/_stats/refresh"
GET, "/_stats/search"
GET, "/_stats/search/{searchGroupsStats1}"
GET, "/_stats/store"
GET, "/_stats/warmer"
GET, "/_template/{name}"
GET, "/_validate/query"
GET, "/{index}/_aliases"
GET, "/{index}/_analyze"
GET, "/{index}/_cache/clear"
GET, "/{index}/_flush"
GET, "/{index}/_mget"
GET, "/{index}/_optimize"
GET, "/{index}/_refresh"
GET, "/{index}/_segments"
GET, "/{index}/_settings"
GET, "/{index}/_stats"
GET, "/{index}/_stats/docs"
GET, "/{index}/_stats/flush"
GET, "/{index}/_stats/get"
GET, "/{index}/_stats/indexing"
GET, "/{index}/_stats/indexing/{indexingTypes2}"
GET, "/{index}/_stats/merge"
GET, "/{index}/_stats/refresh"
GET, "/{index}/_stats/search"
GET, "/{index}/_stats/search/{searchGroupsStats2}"
GET, "/{index}/_stats/store"
GET, "/{index}/_stats/warmer"
GET, "/{index}/_validate/query"
GET, "/{index}/_warmer"
GET, "/{index}/_warmer/{name}"
GET, "/{index}/{type}/_mget"
GET, "/{index}/{type}/_msearch"
GET, "/{index}/{type}/_percolate"
GET, "/{index}/{type}/_validate/query"
GET, "/{index}/{type}/_warmer/{name}"
GET, "/{index}/{type}/{id}"
GET, "/{index}/{type}/{id}/_explain"
GET, "/{index}/{type}/{id}/_mlt"
GET, "_stats/docs"
HEAD, "/"
HEAD, "/{index}"
HEAD, "/{index}/{type}"
HEAD, "/{index}/{type}/{id}"
POST, "/_aliases"
POST, "/_analyze"
POST, "/_bulk"
POST, "/_cache/clear"
POST, "/_cluster/nodes/_restart"
POST, "/_cluster/nodes/{nodeId}/_restart"
POST, "/_cluster/reroute"
POST, "/_flush"
POST, "/_gateway/snapshot"
POST, "/_mget"
POST, "/_msearch"
POST, "/_optimize"
POST, "/_refresh"
POST, "/_search/scroll"
POST, "/_search/scroll/{scroll_id}"
POST, "/_template/{name}"
POST, "/_validate/query"
POST, "/{index}"   SAME AS PUT
POST, "/{index}/_analyze"
POST, "/{index}/_bulk"
POST, "/{index}/_cache/clear"
POST, "/{index}/_close"
POST, "/{index}/_count"
POST, "/{index}/_flush"
POST, "/{index}/_gateway/snapshot"
POST, "/{index}/_mget"
POST, "/{index}/_msearch"
POST, "/{index}/_open"
POST, "/{index}/_optimize"
POST, "/{index}/_refresh"
POST, "/{index}/_validate/query"
POST, "/{index}/{type}"
POST, "/{index}/{type}/_bulk"
POST, "/{index}/{type}/_count"
POST, "/{index}/{type}/_mget"
POST, "/{index}/{type}/_msearch"
POST, "/{index}/{type}/_percolate"
POST, "/{index}/{type}/_validate/query"
POST, "/{index}/{type}/{id}/_create"
POST, "/{index}/{type}/{id}/_mlt"
POST, "/{index}/{type}/{id}/_update"
PUT, "/_bulk"
PUT, "/_cluster/settings"
PUT, "/_settings"
PUT, "/_template/{name}"
PUT, "/{index}/_bulk"
PUT, "/{index}/_settings"
PUT, "/{index}/_warmer/{name}"
PUT, "/{index}/{type}/_bulk"
PUT, "/{index}/{type}/_warmer/{name}"
PUT, "/{index}/{type}/{id}/_create"
```

Ignored APIs
------------

```
GET, "/_search"
GET, "/{index}/_search"
GET, "/{index}/{type}/_search"
```
