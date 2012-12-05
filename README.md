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

| API Name             |    Method     |               URL                          |                                Documentation                                      | 
|----------------------|:-------------:|--------------------------------------------|-----------------------------------------------------------------------------------|
| welcome              |     GET       | /                                          |                                                                                   |
| create_index         |     PUT       | /{index}                                   |http://www.elasticsearch.org/guide/reference/api/admin-indices-create-index.html   |
| delete_index         |    DELETE     | /{index}                                   |http://www.elasticsearch.org/guide/reference/api/admin-indices-delete-index.html   |
| put_mapping          |     PUT       | /{index}/_mapping                          |http://www.elasticsearch.org/guide/reference/api/admin-indices-put-mapping.html    |
| get_mapping          |     GET       | /{index}/{type}/_mapping                   |http://www.elasticsearch.org/guide/reference/api/admin-indices-get-mapping.html    |
| delete_mapping       |    DELETE     | /{index}/{type}                            |http://www.elasticsearch.org/guide/reference/api/admin-indices-delete-mapping.html |
| index                |     POST      | /{index}/{type}/{id}                       |http://www.elasticsearch.org/guide/reference/api/index_.html                       |
| search               |     POST      | /{index}/{type}/_search                    |http://www.elasticsearch.org/guide/reference/api/search/                           |
| delete               |    DELETE     | /{index}/{type}/{id}                       |http://www.elasticsearch.org/guide/reference/api/delete.html                       |
| analyze              |     GET       | /_analyze                                  |http://www.elasticsearch.org/guide/reference/api/admin-indices-analyze.html        |
| count                |     POST      | /_count                                    |http://www.elasticsearch.org/guide/reference/api/count.html                        |
| status               |     GET       | /{index}/_status                           |http://www.elasticsearch.org/guide/reference/api/admin-indices-status.html         |
| shutdown             |     POST      | /_cluster/nodes/{nodes}/_shutdown          |http://www.elasticsearch.org/guide/reference/api/admin-cluster-nodes-shutdown.html |
| nodes_stats          |     GET       | /_nodes/{nodeId}/stats/{stats}             |http://www.elasticsearch.org/guide/reference/api/admin-cluster-nodes-stats.html    |
| cluster_state        |     GET       | /_cluster/state                            |http://www.elasticsearch.org/guide/reference/api/admin-cluster-state.html          | 
| cluster_settings     |     GET       | /_cluster/settings                         |Not documented                                                                     |




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

| API Name             |    Method     |               URL                          |                                Documentation                                      | 
|----------------------|:-------------:|--------------------------------------------|-----------------------------------------------------------------------------------|
|                      |    DELETE     | /_template/{name}                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |    DELETE     | /{index}/_query                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |    DELETE     | /{index}/_warmer                           |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |    DELETE     | /{index}/_warmer/{name}                    |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |    DELETE     | /{index}/{type}/_query                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |    DELETE     | /{index}/{type}/_warmer/{name}             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      |  /_aliases                                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_cache/clear                              |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_cluster/health/{index}                   |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_cluster/nodes/{nodeId}                   |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_cluster/nodes/{nodeId}/hot_threads       |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_flush                                    |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_mget                                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}                           |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/fs/stats                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/hot_threads               |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/http                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/http/stats                |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/indices/stats             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/jvm                       |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/jvm/stats                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/network                   |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/network/stats             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/os                        |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/os/stats                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/process                   |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/process/stats             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/settings                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/stats                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/stats/fs                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/stats/http                |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/stats/indices             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/stats/jvm                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/stats/network             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/stats/os                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/stats/process             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/stats/thread_pool         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/stats/transport           |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/thread_pool               |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/thread_pool/stats         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/transport                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_nodes/{nodeId}/transport/stats           |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_optimize                                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_refresh                                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_search/scroll                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_search/scroll/{scroll_id}                |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_segments                                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_settings                                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_stats                                    |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_stats/flush                              |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_stats/get                                |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_stats/indexing                           |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_stats/indexing/{indexingTypes1}          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_stats/merge                              |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_stats/refresh                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_stats/search                             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_stats/search/{searchGroupsStats1}        |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_stats/store                              |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_stats/warmer                             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_template/{name}                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_validate/query                           |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_aliases                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_analyze                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_cache/clear                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_flush                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_mget                             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_optimize                         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_refresh                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_segments                         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_settings                         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats/docs                       |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats/flush                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats/get                        |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats/indexing                   |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats/indexing/{indexingTypes2}  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats/merge                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats/refresh                    |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats/search                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats/search/{searchGroupsStats2}|http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats/store                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_stats/warmer                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_validate/query                   |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_warmer                           |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_warmer/{name}                    |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/{type}/_mget                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/{type}/_percolate                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/{type}/_validate/query            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/{type}/_warmer/{name}             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/{type}/{id}                       |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/{type}/{id}/_explain              |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/{type}/{id}/_mlt                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | _stats/docs                                |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      HEAD     | /                                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      HEAD     | /{index}                                   |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      HEAD     | /{index}/{type}                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      HEAD     | /{index}/{type}/{id}                       |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_aliases                                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_analyze                                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_bulk                                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_cache/clear                              |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_cluster/nodes/_restart                   |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_cluster/nodes/{nodeId}/_restart          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_cluster/reroute                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_flush                                    |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_gateway/snapshot                         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_mget                                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_optimize                                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_refresh                                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_search/scroll                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_search/scroll/{scroll_id}                |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_template/{name}                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_validate/query                           |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}   SAME AS PUT                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_analyze                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_bulk                             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_cache/clear                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_close                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_count                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_flush                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_gateway/snapshot                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_mget                             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_open                             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_optimize                         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_refresh                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_validate/query                   |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/{type}                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/{type}/_bulk                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/{type}/_count                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/{type}/_mget                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/{type}/_percolate                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/{type}/_validate/query            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/{type}/{id}/_create               |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/{type}/{id}/_mlt                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/{type}/{id}/_update               |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /_bulk                                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /_cluster/settings                         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /_settings                                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /_template/{name}                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /{index}/_bulk                             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /{index}/_settings                         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /{index}/_warmer/{name}                    |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /{index}/{type}/_bulk                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /{index}/{type}/_warmer/{name}             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /{index}/{type}/{id}/_create               |http://www.elasticsearch.org/guide/reference/api/xxx                               |


Known issues
------------

| API Name             |    Method     |               URL                          |                                Documentation                                      | 
|----------------------|:-------------:|--------------------------------------------|-----------------------------------------------------------------------------------|
| multi_search         |     POST      | /{index}/{type}/_msearch                   |Doesn't work as --data-binary is required instead of -d                            |


Ignored APIs
------------


```
GET, "/{index}/{type}/_search"
GET, "/{index}/{type}/_msearch"
```
