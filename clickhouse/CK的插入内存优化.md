# CK的插入内存优化



## 1M

### 压缩参数1M-1万行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 20.54 GiB.       |      |
| 时间 | 4.606923895 sec. |      |
|      |                  |      |



```sh
[mwrpt-clickhouse] 2024.11.20 05:41:58.790761 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 129.53 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:41:58.790876 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Trace> DiskLocal: Reserved 129.53 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:41:58.919831 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:41:59.268536 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:41:59.292905 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:41:59.317355 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:41:59.342147 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:41:59.370122 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:41:59.418582 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:41:59.453216 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:41:59.478907 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:41:59.505557 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:41:59.877429 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Trace> MergedBlockOutputStream: filled checksums 20240805_1919_1919_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:41:59.922386 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 48.81 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:41:59.922481 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Trace> DiskLocal: Reserved 48.81 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:41:59.976615 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 11.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:42:00.007297 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 12.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:42:00.035909 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 13.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:42:00.062081 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 14.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:42:00.087363 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 15.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:42:00.112021 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 16.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:42:00.136833 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 17.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:42:00.162990 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 18.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:42:00.188852 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 19.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:42:00.214817 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Current memory usage (for query): 20.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:42:00.452674 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Trace> MergedBlockOutputStream: filled checksums 20240806_1920_1920_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:42:00.578951 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1919_1919_0
[mwrpt-clickhouse] 2024.11.20 05:42:00.580140 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1919_1919_0 to 20240805_1917_1917_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:42:00.671297 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1920_1920_0
[mwrpt-clickhouse] 2024.11.20 05:42:00.672439 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1920_1920_0 to 20240806_1918_1918_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:42:00.676944 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> executeQuery: Read 10000 rows, 173.41 MiB in 4.599387 sec., 2174.2027796312855 rows/sec., 37.70 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 05:42:00.683565 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> MemoryTracker: Peak memory usage (for query): 20.54 GiB.
[mwrpt-clickhouse] 2024.11.20 05:42:00.683620 [ 879 ] {d9c8933e-2ef2-46d5-a50f-bc5dd429dd74} <Debug> TCPHandler: Processed in 4.606923895 sec.
```

### 压缩参数1M-8K行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 20.37 GiB.       |      |
| 时间 | 3.989384984 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 05:44:38.024434 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 102.74 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:44:38.024570 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Trace> DiskLocal: Reserved 102.74 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.111204 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.346769 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.369809 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.392214 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.415512 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.438156 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.465567 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.490500 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.515567 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.541043 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.842577 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Trace> MergedBlockOutputStream: filled checksums 20240805_1921_1921_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:44:38.885986 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 39.93 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:44:38.886083 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Trace> DiskLocal: Reserved 39.93 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:44:38.932413 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 11.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:39.092627 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 12.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:39.116925 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 13.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:39.140253 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 14.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:39.165066 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 15.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:39.188928 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 16.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:39.213512 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 17.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:39.237368 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 18.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:39.262446 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 19.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:39.296168 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Current memory usage (for query): 20.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:39.606047 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Trace> MergedBlockOutputStream: filled checksums 20240806_1922_1922_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:44:39.740158 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1921_1921_0
[mwrpt-clickhouse] 2024.11.20 05:44:39.741231 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1921_1921_0 to 20240805_1919_1919_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:44:39.836102 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1922_1922_0
[mwrpt-clickhouse] 2024.11.20 05:44:39.837211 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1922_1922_0 to 20240806_1920_1920_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:44:39.841486 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> executeQuery: Read 8000 rows, 138.72 MiB in 3.981851 sec., 2009.1158609400504 rows/sec., 34.84 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 05:44:39.847909 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> MemoryTracker: Peak memory usage (for query): 20.37 GiB.
[mwrpt-clickhouse] 2024.11.20 05:44:39.847949 [ 879 ] {d2ecafe5-fc8a-4918-a190-5390df8ea641} <Debug> TCPHandler: Processed in 3.989384984 sec.
```



### 压缩参数1M-6K行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 20.31 GiB.       |      |
| 时间 | 3.281427192 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 05:47:15.442608 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 77.66 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:47:15.442728 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Trace> DiskLocal: Reserved 77.66 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:47:15.589439 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:15.614359 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:15.639156 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:15.663957 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:15.688694 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:15.713020 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:15.739517 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:15.765335 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:15.791236 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:15.817679 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.084157 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Trace> MergedBlockOutputStream: filled checksums 20240805_1923_1923_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:47:16.126581 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 29.34 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:47:16.126672 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Trace> DiskLocal: Reserved 29.34 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.189901 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 11.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.215305 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 12.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.240828 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 13.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.264730 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 14.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.289609 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 15.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.314672 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 16.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.339708 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 17.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.364457 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 18.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.391025 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 19.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.415876 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Current memory usage (for query): 20.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.602124 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Trace> MergedBlockOutputStream: filled checksums 20240806_1924_1924_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:47:16.740758 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1923_1923_0
[mwrpt-clickhouse] 2024.11.20 05:47:16.741807 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1923_1923_0 to 20240805_1921_1921_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:47:16.908443 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1924_1924_0
[mwrpt-clickhouse] 2024.11.20 05:47:16.909633 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1924_1924_0 to 20240806_1922_1922_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:47:16.914413 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> executeQuery: Read 6000 rows, 104.04 MiB in 3.274239 sec., 1832.4868771033512 rows/sec., 31.78 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 05:47:16.920557 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> MemoryTracker: Peak memory usage (for query): 20.31 GiB.
[mwrpt-clickhouse] 2024.11.20 05:47:16.920594 [ 879 ] {327c2df1-9de6-44e2-ab77-97b8010a6684} <Debug> TCPHandler: Processed in 3.281427192 sec.
```



### 压缩参数1M-4K行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 20.19 GiB.       |      |
| 时间 | 2.800616272 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 05:48:10.545462 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 52.49 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:48:10.545576 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Trace> DiskLocal: Reserved 52.49 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:48:10.682100 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:10.712604 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:10.736680 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:10.762486 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:10.788373 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:10.812303 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:10.839249 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:10.863393 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:10.887556 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:10.912128 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.165887 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Trace> MergedBlockOutputStream: filled checksums 20240805_1925_1925_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:48:11.206009 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 18.84 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:48:11.206095 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Trace> DiskLocal: Reserved 18.84 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.468954 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 11.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.493100 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 12.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.517679 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 13.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.540849 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 14.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.565037 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 15.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.588186 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 16.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.611934 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 17.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.635742 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 18.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.663067 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 19.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.689241 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Current memory usage (for query): 20.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:11.928822 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Trace> MergedBlockOutputStream: filled checksums 20240806_1926_1926_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:48:12.087232 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1925_1925_0
[mwrpt-clickhouse] 2024.11.20 05:48:12.088422 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1925_1925_0 to 20240805_1923_1923_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:48:12.181876 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1926_1926_0
[mwrpt-clickhouse] 2024.11.20 05:48:12.183178 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1926_1926_0 to 20240806_1924_1924_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:48:12.187758 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> executeQuery: Read 4000 rows, 69.35 MiB in 2.794252 sec., 1431.5101143347129 rows/sec., 24.82 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 05:48:12.193246 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> MemoryTracker: Peak memory usage (for query): 20.19 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:12.193281 [ 879 ] {43eb895d-8665-408c-bdb0-42d7fc5c50c4} <Debug> TCPHandler: Processed in 2.800616272 sec.
```

### 压缩参数1M-3K行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值              | 描述 |
| ---- | --------------- | ---- |
| 内存 | 20.16 GiB.      |      |
| 时间 | 2.383738952 sec |      |
|      |                 |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 06:16:43.806145 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 38.76 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 06:16:43.806259 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Trace> DiskLocal: Reserved 38.76 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 06:16:43.875472 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:43.918896 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:43.962961 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.056668 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.090863 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.120225 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.154247 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.185664 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.221834 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.258268 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.477887 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Trace> MergedBlockOutputStream: filled checksums 20240805_1959_1959_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 06:16:44.521017 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 14.74 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 06:16:44.521113 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Trace> DiskLocal: Reserved 14.74 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.564289 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 11.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.588239 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 12.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.611506 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 13.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.634940 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 14.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.658525 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 15.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.682312 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 16.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.707358 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 17.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.731375 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 18.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.755685 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 19.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.781007 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Current memory usage (for query): 20.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:44.949009 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Trace> MergedBlockOutputStream: filled checksums 20240806_1960_1960_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 06:16:45.063074 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1959_1959_0
[mwrpt-clickhouse] 2024.11.20 06:16:45.064191 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1959_1959_0 to 20240805_1957_1957_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 06:16:45.155288 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1960_1960_0
[mwrpt-clickhouse] 2024.11.20 06:16:45.156476 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1960_1960_0 to 20240806_1958_1958_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 06:16:45.160553 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> executeQuery: Read 3000 rows, 52.02 MiB in 2.376854 sec., 1262.1726029449012 rows/sec., 21.88 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 06:16:45.166021 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> MemoryTracker: Peak memory usage (for query): 20.16 GiB.
[mwrpt-clickhouse] 2024.11.20 06:16:45.166052 [ 879 ] {2c5a7359-5b6e-4a9d-8a0b-9fefdb7f0b2f} <Debug> TCPHandler: Processed in 2.383738952 sec.
root@mwrpt-clickhouse:/home/data# 
```



### 压缩参数1M-2.5K行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 20.14 GiB.       |      |
| 时间 | 2.310908969 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 06:22:55.737520 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 31.66 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 06:22:55.737628 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> DiskLocal: Reserved 31.66 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.027752 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.073362 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.097530 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.121156 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.144662 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.168053 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.192533 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.218140 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.242478 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.267140 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.477781 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> MergedBlockOutputStream: filled checksums 20240805_1961_1961_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 06:22:56.520626 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 12.92 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 06:22:56.520717 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> DiskLocal: Reserved 12.92 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.548777 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 11.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.569870 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 12.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.590973 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 13.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.612933 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 14.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.634709 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 15.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.656235 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 16.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.684323 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 17.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.706765 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 18.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.729670 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 19.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.752576 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 20.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.916617 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> MergedBlockOutputStream: filled checksums 20240806_1962_1962_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 06:22:57.028286 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1961_1961_0
[mwrpt-clickhouse] 2024.11.20 06:22:57.029423 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1961_1961_0 to 20240805_1959_1959_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 06:22:57.115799 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1962_1962_0
[mwrpt-clickhouse] 2024.11.20 06:22:57.118254 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1962_1962_0 to 20240806_1960_1960_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 06:22:57.122727 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> executeQuery: Read 2500 rows, 43.35 MiB in 2.304126 sec., 1085.0101079541657 rows/sec., 18.81 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 06:22:57.128529 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Peak memory usage (for query): 20.14 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:57.128564 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> TCPHandler: Processed in 2.310908969 sec.
```



### 压缩参数1M-2.25K行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 20.13 GiB.       |      |
| 时间 | 2.482421298 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 06:22:55.737520 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 31.66 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 06:22:55.737628 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> DiskLocal: Reserved 31.66 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.027752 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.073362 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.097530 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.121156 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.144662 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.168053 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.192533 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.218140 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.242478 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.267140 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.477781 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> MergedBlockOutputStream: filled checksums 20240805_1961_1961_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 06:22:56.520626 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 12.92 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 06:22:56.520717 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> DiskLocal: Reserved 12.92 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.548777 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 11.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.569870 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 12.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.590973 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 13.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.612933 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 14.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.634709 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 15.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.656235 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 16.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.684323 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 17.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.706765 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 18.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.729670 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 19.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.752576 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Current memory usage (for query): 20.00 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:56.916617 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> MergedBlockOutputStream: filled checksums 20240806_1962_1962_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 06:22:57.028286 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1961_1961_0
[mwrpt-clickhouse] 2024.11.20 06:22:57.029423 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1961_1961_0 to 20240805_1959_1959_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 06:22:57.115799 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1962_1962_0
[mwrpt-clickhouse] 2024.11.20 06:22:57.118254 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1962_1962_0 to 20240806_1960_1960_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 06:22:57.122727 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> executeQuery: Read 2500 rows, 43.35 MiB in 2.304126 sec., 1085.0101079541657 rows/sec., 18.81 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 06:22:57.128529 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> MemoryTracker: Peak memory usage (for query): 20.14 GiB.
[mwrpt-clickhouse] 2024.11.20 06:22:57.128564 [ 879 ] {71631266-333e-47fc-8ee9-85de0979edec} <Debug> TCPHandler: Processed in 2.310908969 sec.
```







### 压缩参数1M-2K行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值              | 描述 |
| ---- | --------------- | ---- |
| 内存 | 10.13 GiB.      |      |
| 时间 | 1.46919263 sec. |      |
|      |                 |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 05:48:52.509985 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 25.67 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:48:52.510100 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Trace> DiskLocal: Reserved 25.67 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:48:52.562651 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:52.587577 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:52.612303 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:52.638378 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:52.663465 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:52.688199 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:52.713613 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:52.738883 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:52.764307 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:52.792046 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:52.995910 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Trace> MergedBlockOutputStream: filled checksums 20240805_1927_1927_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:48:53.041709 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 10.00 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:48:53.041808 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Trace> DiskLocal: Reserved 10.00 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:48:53.095825 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Trace> MergedBlockOutputStream: filled checksums 20240806_1928_1928_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:48:53.189071 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1927_1927_0
[mwrpt-clickhouse] 2024.11.20 05:48:53.190246 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1927_1927_0 to 20240805_1925_1925_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:48:53.200508 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1928_1928_0
[mwrpt-clickhouse] 2024.11.20 05:48:53.200634 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1928_1928_0 to 20240806_1926_1926_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:48:53.203530 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> executeQuery: Read 2000 rows, 34.68 MiB in 1.462777 sec., 1367.2624056845302 rows/sec., 23.71 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 05:48:53.209111 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> MemoryTracker: Peak memory usage (for query): 10.13 GiB.
[mwrpt-clickhouse] 2024.11.20 05:48:53.209154 [ 879 ] {ab06831d-fb7b-4698-95ed-0bcb09ea1ec6} <Debug> TCPHandler: Processed in 1.46919263 sec.
```

### 压缩参数1M-1K行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 10.08 GiB.       |      |
| 时间 | 1.220871978 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 05:49:31.686376 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 12.73 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:49:31.686500 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Trace> DiskLocal: Reserved 12.73 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:49:31.778186 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:49:31.804479 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:49:31.830026 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:49:31.863034 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:49:31.891410 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:49:31.916930 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:49:31.942549 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:49:31.967472 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:49:31.992539 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:49:32.020287 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:49:32.196745 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Trace> MergedBlockOutputStream: filled checksums 20240805_1929_1929_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:49:32.237494 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 5.10 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:49:32.237578 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Trace> DiskLocal: Reserved 5.10 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:49:32.283472 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Trace> MergedBlockOutputStream: filled checksums 20240806_1930_1930_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:49:32.376197 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1929_1929_0
[mwrpt-clickhouse] 2024.11.20 05:49:32.377305 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1929_1929_0 to 20240805_1927_1927_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:49:32.386490 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1930_1930_0
[mwrpt-clickhouse] 2024.11.20 05:49:32.386597 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1930_1930_0 to 20240806_1928_1928_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:49:32.388991 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> executeQuery: Read 1000 rows, 17.34 MiB in 1.21492 sec., 823.0994633391499 rows/sec., 14.27 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 05:49:32.394109 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> MemoryTracker: Peak memory usage (for query): 10.08 GiB.
[mwrpt-clickhouse] 2024.11.20 05:49:32.394145 [ 879 ] {c159bb24-ed8f-41c2-bd2f-d7575d28245e} <Debug> TCPHandler: Processed in 1.220871978 sec.
```



### 压缩参数1M-8百行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值              | 描述 |
| ---- | --------------- | ---- |
| 内存 | 10.07 GiB.      |      |
| 时间 | 1.593221748 sec |      |
|      |                 |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 05:50:35.293771 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 10.24 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:50:35.293899 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Trace> DiskLocal: Reserved 10.24 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:50:35.647123 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:50:35.823453 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:50:35.849831 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:50:35.884950 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:50:35.909149 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:50:35.932982 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:50:35.958126 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:50:35.982003 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:50:36.006185 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:50:36.032707 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 05:50:36.209630 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Trace> MergedBlockOutputStream: filled checksums 20240805_1931_1931_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:50:36.252732 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 4.03 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:50:36.252839 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Trace> DiskLocal: Reserved 4.03 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:50:36.299089 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Trace> MergedBlockOutputStream: filled checksums 20240806_1932_1932_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:50:36.395317 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1931_1931_0
[mwrpt-clickhouse] 2024.11.20 05:50:36.396183 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1931_1931_0 to 20240805_1929_1929_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:50:36.405184 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1932_1932_0
[mwrpt-clickhouse] 2024.11.20 05:50:36.405308 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1932_1932_0 to 20240806_1930_1930_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:50:36.408118 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> executeQuery: Read 800 rows, 13.87 MiB in 1.587096 sec., 504.0652865359121 rows/sec., 8.74 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 05:50:36.413413 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> MemoryTracker: Peak memory usage (for query): 10.07 GiB.
[mwrpt-clickhouse] 2024.11.20 05:50:36.413447 [ 879 ] {ff75c7a2-6564-41c8-a7fe-10f1c1b00520} <Debug> TCPHandler: Processed in 1.593221748 sec.
```



### 压缩参数1M-6百行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 88.93 MB         |      |
| 时间 | 0.574109081 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 05:51:14.226734 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 7.56 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:51:14.226856 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Trace> DiskLocal: Reserved 7.56 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:51:14.279093 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Trace> MergedBlockOutputStream: filled checksums 20240805_1933_1933_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:51:14.303469 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 3.13 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:51:14.303546 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Trace> DiskLocal: Reserved 3.13 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:51:14.344766 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Trace> MergedBlockOutputStream: filled checksums 20240806_1934_1934_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:51:14.365063 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1933_1933_0
[mwrpt-clickhouse] 2024.11.20 05:51:14.365201 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1933_1933_0 to 20240805_1931_1931_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:51:14.372067 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1934_1934_0
[mwrpt-clickhouse] 2024.11.20 05:51:14.372167 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1934_1934_0 to 20240806_1932_1932_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:51:14.374492 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Debug> executeQuery: Read 600 rows, 10.40 MiB in 0.56802 sec., 1056.3008344776592 rows/sec., 18.31 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 05:51:14.379753 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Debug> MemoryTracker: Peak memory usage (for query): 88.93 MiB.
[mwrpt-clickhouse] 2024.11.20 05:51:14.379805 [ 879 ] {85d6e666-9817-4ece-ad34-aa30b79e1959} <Debug> TCPHandler: Processed in 0.574109081 sec.
```



### 压缩参数1M-5百行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 89.08 MiB.       |      |
| 时间 | 0.544079557 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 05:51:59.197516 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 6.31 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:51:59.197623 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Trace> DiskLocal: Reserved 6.31 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:51:59.248551 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Trace> MergedBlockOutputStream: filled checksums 20240805_1935_1935_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:51:59.274302 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 2.60 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:51:59.274398 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Trace> DiskLocal: Reserved 2.60 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:51:59.317075 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Trace> MergedBlockOutputStream: filled checksums 20240806_1936_1936_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:51:59.337033 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1935_1935_0
[mwrpt-clickhouse] 2024.11.20 05:51:59.337158 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1935_1935_0 to 20240805_1933_1933_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:51:59.343574 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1936_1936_0
[mwrpt-clickhouse] 2024.11.20 05:51:59.343670 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1936_1936_0 to 20240806_1934_1934_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:51:59.346101 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Debug> executeQuery: Read 500 rows, 8.67 MiB in 0.538018 sec., 929.3369366824159 rows/sec., 16.11 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 05:51:59.351313 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Debug> MemoryTracker: Peak memory usage (for query): 89.08 MiB.
[mwrpt-clickhouse] 2024.11.20 05:51:59.351363 [ 879 ] {ead3fe8c-4bb8-423a-8e08-8c021b49b537} <Debug> TCPHandler: Processed in 0.544079557 sec.
```



### 压缩参数1M-4百行

参数：

| 参数                    | 值          | 描述 |
| ----------------------- | ----------- | ---- |
| max_memory_usage        | 41000000000 | 40G  |
| max_compress_block_size | 1048576     | 1MB  |
|                         |             |      |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 76.73 MiB        |      |
| 时间 | 0.570830875 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 05:52:49.040183 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 4.85 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:52:49.040301 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Trace> DiskLocal: Reserved 4.85 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:52:49.094271 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Trace> MergedBlockOutputStream: filled checksums 20240805_1937_1937_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:52:49.123016 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Trying to reserve 2.28 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 05:52:49.123100 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Trace> DiskLocal: Reserved 2.28 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 05:52:49.170505 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Trace> MergedBlockOutputStream: filled checksums 20240806_1938_1938_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 05:52:49.193092 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1937_1937_0
[mwrpt-clickhouse] 2024.11.20 05:52:49.193238 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240805_1937_1937_0 to 20240805_1935_1935_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:52:49.201164 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Debug> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_1938_1938_0
[mwrpt-clickhouse] 2024.11.20 05:52:49.201273 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Trace> maxwell_mes.his_wafer_sorting_order (4879a5f9-2f72-48cd-b181-b0a8195c0850): Renaming temporary part tmp_insert_20240806_1938_1938_0 to 20240806_1936_1936_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 05:52:49.204149 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Debug> executeQuery: Read 400 rows, 6.94 MiB in 0.564265 sec., 708.8867819198426 rows/sec., 12.29 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 05:52:49.209874 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Debug> MemoryTracker: Peak memory usage (for query): 76.73 MiB.
[mwrpt-clickhouse] 2024.11.20 05:52:49.209913 [ 879 ] {996d2ae0-ec26-4e9b-b514-c14b28ce7589} <Debug> TCPHandler: Processed in 0.570830875 sec.
```





## 512K

### 压缩参数512K-1万行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 10.95 GiB.       |      |
| 时间 | 4.732578101 sec. |      |
|      |                  |      |



```sh
[mwrpt-clickhouse] 2024.11.20 07:09:33.940904 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 128.68 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:09:33.941005 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Trace> DiskLocal: Reserved 128.68 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:09:34.000261 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:09:34.072071 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:09:34.172502 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:09:34.218362 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:09:34.302146 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:09:34.882577 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Trace> MergedBlockOutputStream: filled checksums 20240805_1_1_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:09:34.926621 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 49.66 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:09:34.926710 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Trace> DiskLocal: Reserved 49.66 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:09:34.957840 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:09:35.007486 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:09:35.052029 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:09:35.140435 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:09:35.190404 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:09:35.562475 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Trace> MergedBlockOutputStream: filled checksums 20240806_2_2_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:09:35.698474 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_1_1_0
[mwrpt-clickhouse] 2024.11.20 07:09:35.699613 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_1_1_0 to 20240805_276180_276180_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:09:35.789622 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_2_2_0
[mwrpt-clickhouse] 2024.11.20 07:09:35.790803 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_2_2_0 to 20240806_276181_276181_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:09:35.795249 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> executeQuery: Read 10000 rows, 173.40 MiB in 4.726772 sec., 2115.6087071684437 rows/sec., 36.69 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:09:35.800019 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> MemoryTracker: Peak memory usage (for query): 10.95 GiB.
[mwrpt-clickhouse] 2024.11.20 07:09:35.800051 [ 891 ] {50ae9aca-606e-4ac6-abaf-215dd2badb68} <Debug> TCPHandler: Processed in 4.732578101 sec.
```

### 压缩参数512K-8K行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 10.77 GiB.       |      |
| 时间 | 3.768631682 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 07:10:26.684020 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 102.49 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:10:26.684130 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Trace> DiskLocal: Reserved 102.49 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:10:26.798407 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:26.869080 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:26.918150 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:26.967317 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:27.018035 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:27.509151 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Trace> MergedBlockOutputStream: filled checksums 20240805_3_3_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:10:27.549447 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 40.17 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:10:27.549540 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Trace> DiskLocal: Reserved 40.17 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:10:27.583379 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:27.626956 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:27.672185 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:27.718736 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:27.764670 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:27.800461 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Current memory usage (total): 15.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:28.068032 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Trace> MergedBlockOutputStream: filled checksums 20240806_4_4_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:10:28.195531 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_3_3_0
[mwrpt-clickhouse] 2024.11.20 07:10:28.196636 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_3_3_0 to 20240805_276182_276182_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:10:28.276580 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_4_4_0
[mwrpt-clickhouse] 2024.11.20 07:10:28.277595 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_4_4_0 to 20240806_276183_276183_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:10:28.281687 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> executeQuery: Read 8000 rows, 138.71 MiB in 3.762553 sec., 2126.215896493684 rows/sec., 36.87 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:10:28.286800 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> MemoryTracker: Peak memory usage (for query): 10.77 GiB.
[mwrpt-clickhouse] 2024.11.20 07:10:28.286837 [ 891 ] {dd7e9af4-6742-46ff-aac3-5b7b0eee1477} <Debug> TCPHandler: Processed in 3.768631682 sec.
```



### 压缩参数512K-6K行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 10.71 GiB.       |      |
| 时间 | 3.503088194 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 07:11:08.987286 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 76.90 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:11:08.987405 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Trace> DiskLocal: Reserved 76.90 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:11:09.054723 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:09.127485 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:09.175905 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:09.224879 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:09.275376 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:09.673778 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Trace> MergedBlockOutputStream: filled checksums 20240805_5_5_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:11:09.715710 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 30.11 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:11:09.715806 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Trace> DiskLocal: Reserved 30.11 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:11:10.035480 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:10.112308 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:10.158318 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:10.206952 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:10.254091 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:10.526038 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Trace> MergedBlockOutputStream: filled checksums 20240806_6_6_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:11:10.646194 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_5_5_0
[mwrpt-clickhouse] 2024.11.20 07:11:10.647682 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_5_5_0 to 20240805_276184_276184_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:11:10.758016 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_6_6_0
[mwrpt-clickhouse] 2024.11.20 07:11:10.759381 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_6_6_0 to 20240806_276185_276185_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:11:10.766099 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> executeQuery: Read 6000 rows, 104.05 MiB in 3.492305 sec., 1718.062998506717 rows/sec., 29.79 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:11:10.776133 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> MemoryTracker: Peak memory usage (for query): 10.71 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:10.776210 [ 891 ] {87aba96f-897b-4e57-a231-0d880278a530} <Debug> TCPHandler: Processed in 3.503088194 sec.
```



### 压缩参数512K-4K行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 10.59 GiB.       |      |
| 时间 | 2.844228418 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 07:11:47.864446 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 51.26 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:11:47.864543 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Trace> DiskLocal: Reserved 51.26 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:11:47.943362 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:47.991425 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:48.037788 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:48.085944 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:48.132826 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:48.488469 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Trace> MergedBlockOutputStream: filled checksums 20240805_7_7_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:11:48.528720 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 20.08 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:11:48.528803 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Trace> DiskLocal: Reserved 20.08 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:11:48.644448 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:48.691811 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:48.737426 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:48.819620 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:48.831803 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (total): 16.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:48.898114 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:48.911889 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Current memory usage (total): 17.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:49.166499 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Trace> MergedBlockOutputStream: filled checksums 20240806_8_8_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:11:49.289696 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_7_7_0
[mwrpt-clickhouse] 2024.11.20 07:11:49.290974 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_7_7_0 to 20240805_276186_276186_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:11:49.385517 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_8_8_0
[mwrpt-clickhouse] 2024.11.20 07:11:49.386589 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_8_8_0 to 20240806_276187_276187_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:11:49.391297 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> executeQuery: Read 4000 rows, 69.37 MiB in 2.838332 sec., 1409.2784071771732 rows/sec., 24.44 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:11:49.396276 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> MemoryTracker: Peak memory usage (for query): 10.59 GiB.
[mwrpt-clickhouse] 2024.11.20 07:11:49.396314 [ 891 ] {49aec678-cc6b-40d2-aeaa-12450aad94dc} <Debug> TCPHandler: Processed in 2.844228418 sec.
```

### 压缩参数512K-3K行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值              | 描述 |
| ---- | --------------- | ---- |
| 内存 | 10.56 GiB.      |      |
| 时间 | 2.33383167 sec. |      |
|      |                 |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 07:12:23.009487 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 38.80 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:12:23.009563 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Trace> DiskLocal: Reserved 38.80 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.062899 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.107425 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.154068 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.201873 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.248782 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.507219 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Trace> MergedBlockOutputStream: filled checksums 20240805_9_9_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:12:23.546186 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 14.70 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:12:23.546268 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Trace> DiskLocal: Reserved 14.70 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.738782 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.784142 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.828870 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.883888 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.896512 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (total): 18.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.951231 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:23.964127 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Current memory usage (total): 19.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:24.178076 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Trace> MergedBlockOutputStream: filled checksums 20240806_10_10_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:12:24.284874 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_9_9_0
[mwrpt-clickhouse] 2024.11.20 07:12:24.285795 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_9_9_0 to 20240805_276188_276188_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:12:24.371676 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_10_10_0
[mwrpt-clickhouse] 2024.11.20 07:12:24.372767 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_10_10_0 to 20240806_276189_276189_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:12:24.376600 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> executeQuery: Read 3000 rows, 52.02 MiB in 2.327977 sec., 1288.672525544711 rows/sec., 22.35 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:12:24.381613 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> MemoryTracker: Peak memory usage (for query): 10.56 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:24.381651 [ 891 ] {3f1af6fd-dc81-45a5-8911-aa0c01b4d9fc} <Debug> TCPHandler: Processed in 2.33383167 sec.
```



### 压缩参数512K-2.5K行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 10.54 GiB.       |      |
| 时间 | 2.243637453 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 07:12:55.250258 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 31.91 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:12:55.250366 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Trace> DiskLocal: Reserved 31.91 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:12:55.382743 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:55.426297 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:55.473272 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:55.521502 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:55.571648 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:55.832596 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Trace> MergedBlockOutputStream: filled checksums 20240805_11_11_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:12:55.872333 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 12.68 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:12:55.872429 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Trace> DiskLocal: Reserved 12.68 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:12:55.944558 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:56.047399 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:56.104792 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:56.162049 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:56.222977 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:56.432553 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Trace> MergedBlockOutputStream: filled checksums 20240806_12_12_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:12:56.545553 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_11_11_0
[mwrpt-clickhouse] 2024.11.20 07:12:56.546652 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_11_11_0 to 20240805_276190_276190_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:12:56.626703 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_12_12_0
[mwrpt-clickhouse] 2024.11.20 07:12:56.627841 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_12_12_0 to 20240806_276191_276191_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:12:56.631854 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> executeQuery: Read 2500 rows, 43.36 MiB in 2.237327 sec., 1117.4048317478848 rows/sec., 19.38 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:12:56.637472 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> MemoryTracker: Peak memory usage (for query): 10.54 GiB.
[mwrpt-clickhouse] 2024.11.20 07:12:56.637504 [ 891 ] {7feb308c-f374-430c-aa7d-5ae27f1e92e5} <Debug> TCPHandler: Processed in 2.243637453 sec.
```



### 压缩参数512K-2.25K行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 10.53 GiB.       |      |
| 时间 | 2.355043445 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 07:13:27.971237 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 29.59 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:13:27.971355 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Trace> DiskLocal: Reserved 29.59 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:13:28.130587 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:13:28.174165 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:13:28.217940 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:13:28.261932 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:13:28.309931 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:13:28.558278 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Trace> MergedBlockOutputStream: filled checksums 20240805_13_13_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:13:28.596985 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 10.53 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:13:28.597070 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Trace> DiskLocal: Reserved 10.53 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:13:28.879258 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> MemoryTracker: Current memory usage (for query): 6.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:13:28.937090 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> MemoryTracker: Current memory usage (for query): 7.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:13:28.980035 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> MemoryTracker: Current memory usage (for query): 8.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:13:29.025764 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> MemoryTracker: Current memory usage (for query): 9.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:13:29.070781 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> MemoryTracker: Current memory usage (for query): 10.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:13:29.277919 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Trace> MergedBlockOutputStream: filled checksums 20240806_14_14_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:13:29.392458 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_13_13_0
[mwrpt-clickhouse] 2024.11.20 07:13:29.393693 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_13_13_0 to 20240805_276192_276192_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:13:29.476813 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_14_14_0
[mwrpt-clickhouse] 2024.11.20 07:13:29.477894 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_14_14_0 to 20240806_276193_276193_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:13:29.482261 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> executeQuery: Read 2250 rows, 39.02 MiB in 2.34914 sec., 957.7973215730012 rows/sec., 16.61 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:13:29.487396 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> MemoryTracker: Peak memory usage (for query): 10.53 GiB.
[mwrpt-clickhouse] 2024.11.20 07:13:29.487434 [ 891 ] {7ab5f60a-aadd-4cbf-a35d-aad41fc340bc} <Debug> TCPHandler: Processed in 2.355043445 sec.
```







### 压缩参数512K-2K行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 5.33 GiB.        |      |
| 时间 | 1.742226094 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 07:14:01.887281 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 25.99 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:14:01.887386 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Trace> DiskLocal: Reserved 25.99 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:14:02.180600 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:14:02.248263 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:14:02.295074 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:14:02.340569 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:14:02.387256 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:14:02.625315 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Trace> MergedBlockOutputStream: filled checksums 20240805_15_15_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:14:02.666462 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 9.68 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:14:02.666538 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Trace> DiskLocal: Reserved 9.68 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:14:02.735123 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Trace> MergedBlockOutputStream: filled checksums 20240806_16_16_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:14:02.829507 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_15_15_0
[mwrpt-clickhouse] 2024.11.20 07:14:02.830625 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_15_15_0 to 20240805_276194_276194_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:14:02.840653 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_16_16_0
[mwrpt-clickhouse] 2024.11.20 07:14:02.840756 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_16_16_0 to 20240806_276195_276195_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:14:02.842862 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Debug> executeQuery: Read 2000 rows, 34.69 MiB in 1.736495 sec., 1151.7453260734987 rows/sec., 19.98 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:14:02.847415 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Debug> MemoryTracker: Peak memory usage (for query): 5.33 GiB.
[mwrpt-clickhouse] 2024.11.20 07:14:02.847444 [ 891 ] {2277e5f6-4c09-460c-875f-8f1a3f67770a} <Debug> TCPHandler: Processed in 1.742226094 sec.
```

### 压缩参数512K-1K行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 5.28 GiB.        |      |
| 时间 | 1.235466571 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 07:16:55.921545 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 12.95 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:16:55.921657 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Trace> DiskLocal: Reserved 12.95 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:16:55.999559 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:16:56.048572 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:16:56.097257 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:16:56.145671 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:16:56.195455 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:16:56.396083 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Trace> MergedBlockOutputStream: filled checksums 20240805_19_19_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:16:56.436292 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 4.88 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:16:56.437622 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Trace> DiskLocal: Reserved 4.88 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:16:56.494940 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Trace> MergedBlockOutputStream: filled checksums 20240806_20_20_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:16:56.595992 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_19_19_0
[mwrpt-clickhouse] 2024.11.20 07:16:56.596989 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_19_19_0 to 20240805_276198_276198_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:16:56.606059 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_20_20_0
[mwrpt-clickhouse] 2024.11.20 07:16:56.606167 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_20_20_0 to 20240806_276199_276199_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:16:56.608583 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Debug> executeQuery: Read 1000 rows, 17.34 MiB in 1.228672 sec., 813.8868632149182 rows/sec., 14.11 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:16:56.614086 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Debug> MemoryTracker: Peak memory usage (for query): 5.28 GiB.
[mwrpt-clickhouse] 2024.11.20 07:16:56.614115 [ 891 ] {05415438-1907-4b07-a419-e8780e1dfb02} <Debug> TCPHandler: Processed in 1.235466571 sec.
```



### 压缩参数512K-8百行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 5.27 GiB.        |      |
| 时间 | 1.221624963 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 07:17:41.084307 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 10.29 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:17:41.084419 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Trace> DiskLocal: Reserved 10.29 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:17:41.150443 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Debug> MemoryTracker: Current memory usage (for query): 1.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:17:41.194790 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Debug> MemoryTracker: Current memory usage (for query): 2.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:17:41.240684 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Debug> MemoryTracker: Current memory usage (for query): 3.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:17:41.287867 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Debug> MemoryTracker: Current memory usage (for query): 4.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:17:41.334308 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Debug> MemoryTracker: Current memory usage (for query): 5.00 GiB.
[mwrpt-clickhouse] 2024.11.20 07:17:41.527586 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Trace> MergedBlockOutputStream: filled checksums 20240805_21_21_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:17:41.572500 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 3.98 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:17:41.572675 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Trace> DiskLocal: Reserved 3.98 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:17:41.649046 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Trace> MergedBlockOutputStream: filled checksums 20240806_22_22_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:17:41.765923 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_21_21_0
[mwrpt-clickhouse] 2024.11.20 07:17:41.767248 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_21_21_0 to 20240805_276200_276200_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:17:41.778420 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_22_22_0
[mwrpt-clickhouse] 2024.11.20 07:17:41.778595 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_22_22_0 to 20240806_276201_276201_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:17:41.781331 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Debug> executeQuery: Read 800 rows, 13.87 MiB in 1.213733 sec., 659.1235469415432 rows/sec., 11.43 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:17:41.788258 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Debug> MemoryTracker: Peak memory usage (for query): 5.27 GiB.
[mwrpt-clickhouse] 2024.11.20 07:17:41.788307 [ 891 ] {6a5dc667-b4c3-43ed-9aa3-7642e53cbb94} <Debug> TCPHandler: Processed in 1.221624963 sec.
```



### 压缩参数512K-6百行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值               | 描述 |
| ---- | ---------------- | ---- |
| 内存 | 89.06 MiB.       |      |
| 时间 | 0.598182437 sec. |      |
|      |                  |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 07:18:15.194955 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 7.83 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:18:15.195054 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Trace> DiskLocal: Reserved 7.83 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:18:15.260478 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Trace> MergedBlockOutputStream: filled checksums 20240805_23_23_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:18:15.291905 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 2.87 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:18:15.291985 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Trace> DiskLocal: Reserved 2.87 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:18:15.343918 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Trace> MergedBlockOutputStream: filled checksums 20240806_24_24_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:18:15.363643 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_23_23_0
[mwrpt-clickhouse] 2024.11.20 07:18:15.363790 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_23_23_0 to 20240805_276202_276202_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:18:15.370584 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_24_24_0
[mwrpt-clickhouse] 2024.11.20 07:18:15.370701 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_24_24_0 to 20240806_276203_276203_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:18:15.373054 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Debug> executeQuery: Read 600 rows, 10.40 MiB in 0.591854 sec., 1013.7635295191044 rows/sec., 17.58 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:18:15.378449 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Debug> MemoryTracker: Peak memory usage (for query): 89.06 MiB.
[mwrpt-clickhouse] 2024.11.20 07:18:15.378496 [ 891 ] {dce21363-dc8d-489e-b719-2cee5461b740} <Debug> TCPHandler: Processed in 0.598182437 sec.
```





### 压缩参数512K-4百行

参数：

| 参数                    | 值          | 描述  |
| ----------------------- | ----------- | ----- |
| max_memory_usage        | 41000000000 | 40G   |
| max_compress_block_size | 524288      | 512KB |
|                         |             |       |

最终结果

| 参数 | 值              | 描述 |
| ---- | --------------- | ---- |
| 内存 | 76.14 MiB.      |      |
| 时间 | 0.55351952 sec. |      |
|      |                 |      |

日志

```sh
[mwrpt-clickhouse] 2024.11.20 07:19:00.688414 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 5.01 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:19:00.688515 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Trace> DiskLocal: Reserved 5.01 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:19:00.773583 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Trace> MergedBlockOutputStream: filled checksums 20240805_25_25_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:19:00.799302 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Trying to reserve 2.12 MiB using storage policy from min volume index 0
[mwrpt-clickhouse] 2024.11.20 07:19:00.799387 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Trace> DiskLocal: Reserved 2.12 MiB on local disk `default`, having unreserved 1.65 TiB.
[mwrpt-clickhouse] 2024.11.20 07:19:00.849222 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Trace> MergedBlockOutputStream: filled checksums 20240806_26_26_0 (state Temporary)
[mwrpt-clickhouse] 2024.11.20 07:19:00.874731 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240805_25_25_0
[mwrpt-clickhouse] 2024.11.20 07:19:00.874881 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240805_25_25_0 to 20240805_276204_276204_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:19:00.882049 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Debug> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145) (DataPartWriter): Spent 0 ms calculating index idx_his_wafer_sorting_end_time for the part 20240806_26_26_0
[mwrpt-clickhouse] 2024.11.20 07:19:00.882182 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Trace> maxwell_mes.his_wafer_sorting (c1c057b2-b834-4c6c-98a4-48b969861145): Renaming temporary part tmp_insert_20240806_26_26_0 to 20240806_276205_276205_0 with tid (1, 1, 00000000-0000-0000-0000-000000000000).
[mwrpt-clickhouse] 2024.11.20 07:19:00.885725 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Debug> executeQuery: Read 400 rows, 6.93 MiB in 0.538818 sec., 742.3656967658839 rows/sec., 12.87 MiB/sec.
[mwrpt-clickhouse] 2024.11.20 07:19:00.899469 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Debug> MemoryTracker: Peak memory usage (for query): 76.14 MiB.
[mwrpt-clickhouse] 2024.11.20 07:19:00.899525 [ 891 ] {d25e3ada-dd37-488b-9fcb-4c0134e95835} <Debug> TCPHandler: Processed in 0.55351952 sec.
```









