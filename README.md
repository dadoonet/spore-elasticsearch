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
|                      |               |                                       |                                                                                   |
|                      |               |                                       |                                                                                   |
|                      |               |                                       |                                                                                   |


TODO
----

