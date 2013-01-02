SPORE Specifications for Elasticsearch
======================================

Welcome to the [Elasticsearch](http://www.elasticsearch.org/) [SPORE](https://github.com/SPORE/specifications) project

Versions
--------

| spore-elasticsearch  | ElasticSearch |  JSpore  |
|:--------------------:|:-------------:|:--------:|
|   master (0.0.1)     |    0.20.2     |  0.0.1   |

Build Status
------------

Thanks to cloudbees for the [build status](https://buildhive.cloudbees.com): [![Build Status](https://buildhive.cloudbees.com/job/dadoonet/job/spore-elasticsearch/badge/icon)](https://buildhive.cloudbees.com/job/dadoonet/job/spore-elasticsearch/)

[![Test trends](https://buildhive.cloudbees.com/job/dadoonet/job/spore-elasticsearch/fr.pilato.elasticsearch$spore-elasticsearch/test/trend)](https://buildhive.cloudbees.com/job/dadoonet/job/spore-elasticsearch/)

Available APIs
--------------

| API Name             |    Method     |               URL                          |                                Documentation                                        |
|----------------------|:-------------:|--------------------------------------------|-------------------------------------------------------------------------------------|
| welcome              |     GET       | /                                          |                                                                                     |
| create_index         |     PUT       | /{index}                                   |http://www.elasticsearch.org/guide/reference/api/admin-indices-create-index.html     |
| delete_index         |    DELETE     | /{index}                                   |http://www.elasticsearch.org/guide/reference/api/admin-indices-delete-index.html     |
| put_mapping          |     PUT       | /{index}/_mapping                          |http://www.elasticsearch.org/guide/reference/api/admin-indices-put-mapping.html      |
| get_mapping          |     GET       | /{index}/{type}/_mapping                   |http://www.elasticsearch.org/guide/reference/api/admin-indices-get-mapping.html      |
| delete_mapping       |    DELETE     | /{index}/{type}                            |http://www.elasticsearch.org/guide/reference/api/admin-indices-delete-mapping.html   |
| index                |     POST      | /{index}/{type}/{id}                       |http://www.elasticsearch.org/guide/reference/api/index_.html                         |
| search               |     POST      | /{index}/{type}/_search                    |http://www.elasticsearch.org/guide/reference/api/search/                             |
| delete               |    DELETE     | /{index}/{type}/{id}                       |http://www.elasticsearch.org/guide/reference/api/delete.html                         |
| analyze              |     GET       | /_analyze                                  |http://www.elasticsearch.org/guide/reference/api/admin-indices-analyze.html          |
| count                |     POST      | /_count                                    |http://www.elasticsearch.org/guide/reference/api/count.html                          |
| status               |     GET       | /{index}/_status                           |http://www.elasticsearch.org/guide/reference/api/admin-indices-status.html           |
| shutdown             |     POST      | /_cluster/nodes/{nodes}/_shutdown          |http://www.elasticsearch.org/guide/reference/api/admin-cluster-nodes-shutdown.html   |
| nodes_stats          |     GET       | /_nodes/{nodeId}/stats/{stats}             |http://www.elasticsearch.org/guide/reference/api/admin-cluster-nodes-stats.html      |
| nodes_info           |     GET       | /_nodes/{nodeId}/{info}                    |http://www.elasticsearch.org/guide/reference/api/admin-cluster-nodes-info.html       |
| cluster_state        |     GET       | /_cluster/state                            |http://www.elasticsearch.org/guide/reference/api/admin-cluster-state.html            |
| cluster_health       |     GET       | /_cluster/health/{index}                   |http://www.elasticsearch.org/guide/reference/api/admin-cluster-health.html           |
| cluster_settings     |     GET       | /_cluster/settings                         |http://www.elasticsearch.org/guide/reference/api/admin-cluster-update-settings.html  |
| put_cluster_settings |     PUT       | /_cluster/settings                         |http://www.elasticsearch.org/guide/reference/api/admin-cluster-update-settings.html  |
| hot_threads          |     GET       | /_nodes/{nodeId}/hot_threads               |http://www.elasticsearch.org/guide/reference/api/admin-cluster-nodes-hot-threads.html|
| multi_search         |     GET       | /{index}/{type}/_msearch                   |http://www.elasticsearch.org/guide/reference/api/multi-search.html                   |
| flush                |     GET       | /{index}/_flush                            |http://www.elasticsearch.org/guide/reference/api/admin-indices-flush.html            |
| index_stats          |     GET       | /{index}/_stats/{stat}/{option}/{type}     |http://www.elasticsearch.org/guide/reference/api/admin-indices-stats.html            |
| index_stats_options  |     GET       | /{index}/_stats/{stat}/{option}/{type}?xxx |http://www.elasticsearch.org/guide/reference/api/admin-indices-stats.html            |
| index_settings       |     GET       | /{index}/_settings                         |http://www.elasticsearch.org/guide/reference/api/admin-indices-get-settings.html     |
| index_put_settings   |     PUT       | /{index}/_settings                         |http://www.elasticsearch.org/guide/reference/api/admin-indices-update-settings.html  |


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
|                      |      GET      | /_aliases                                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_cache/clear                              |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_mget                                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_optimize                                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_refresh                                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_search/scroll                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_search/scroll/{scroll_id}                |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_segments                                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_template/{name}                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /_validate/query                           |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_aliases                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_cache/clear                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_mget                             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_optimize                         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_refresh                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      GET      | /{index}/_segments                         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
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
|                      |      HEAD     | /                                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      HEAD     | /{index}                                   |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      HEAD     | /{index}/{type}                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      HEAD     | /{index}/{type}/{id}                       |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_aliases                                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_bulk                                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_cache/clear                              |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_cluster/nodes/_restart                   |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_cluster/nodes/{nodeId}/_restart          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_cluster/reroute                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_gateway/snapshot                         |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_mget                                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_optimize                                 |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_refresh                                  |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_search/scroll                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_search/scroll/{scroll_id}                |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_template/{name}                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /_validate/query                           |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}   SAME AS PUT                     |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_bulk                             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_cache/clear                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_close                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      POST     | /{index}/_count                            |http://www.elasticsearch.org/guide/reference/api/xxx                               |
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
|                      |      PUT      | /_template/{name}                          |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /{index}/_bulk                             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /{index}/_warmer/{name}                    |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /{index}/{type}/_bulk                      |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /{index}/{type}/_warmer/{name}             |http://www.elasticsearch.org/guide/reference/api/xxx                               |
|                      |      PUT      | /{index}/{type}/{id}/_create               |http://www.elasticsearch.org/guide/reference/api/xxx                               |


Known issues
------------

| API Name             |    Method     |               URL                          |                                Documentation                                      | 
|----------------------|:-------------:|--------------------------------------------|-----------------------------------------------------------------------------------|
| multi_search         |     GET       | /{index}/{type}/_msearch                   |Doesn't work JSpore issue?                                                         |


Ignored APIs
------------


```
GET, "/{index}/{type}/_search"
POST, "/{index}/{type}/_msearch"
POST, "/{index}/_flush"
```
