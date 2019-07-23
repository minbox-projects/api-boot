

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `logging_request_logs`
--

DROP TABLE IF EXISTS `logging_request_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `logging_request_logs` (
  `lrl_id` varchar(36) NOT NULL COMMENT '主键，UUID',
  `lrl_service_detail_id` varchar(36) DEFAULT NULL COMMENT '服务详情编号，关联logging_service_details主键',
  `lrl_trace_id` varchar(36) DEFAULT NULL COMMENT '链路ID',
  `lrl_parent_span_id` varchar(36) DEFAULT NULL COMMENT '上级跨度ID',
  `lrl_span_id` varchar(36) DEFAULT NULL COMMENT '跨度ID',
  `lrl_start_time` mediumtext COMMENT '请求开始时间',
  `lrl_end_time` mediumtext COMMENT '请求结束时间',
  `lrl_http_status` int(11) DEFAULT NULL COMMENT '请求响应状态码',
  `lrl_request_body` longtext COMMENT '请求主体内容',
  `lrl_request_headers` text COMMENT '请求头信息',
  `lrl_request_ip` varchar(30) DEFAULT NULL COMMENT '发起请求客户端的IP地址',
  `lrl_request_method` varchar(10) DEFAULT NULL COMMENT '请求方式',
  `lrl_request_uri` varchar(200) DEFAULT NULL COMMENT '请求路径',
  `lrl_response_body` longtext COMMENT '响应内容',
  `lrl_response_headers` text COMMENT '响应头信息',
  `lrl_time_consuming` int(11) DEFAULT NULL COMMENT '请求耗时',
  `lrl_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志保存时间',
  PRIMARY KEY (`lrl_id`),
  KEY `logging_request_logs_LRL_SERVICE_DETAIL_ID_index` (`lrl_service_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='请求日志信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logging_request_logs`
--

LOCK TABLES `logging_request_logs` WRITE;
/*!40000 ALTER TABLE `logging_request_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `logging_request_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logging_service_details`
--

DROP TABLE IF EXISTS `logging_service_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `logging_service_details` (
  `lsd_id` varchar(36) NOT NULL,
  `lsd_service_id` varchar(200) DEFAULT NULL COMMENT '上报服务的ID，对应spring.application.name配置值',
  `lsd_service_ip` varchar(50) DEFAULT NULL COMMENT '上报服务的IP地址',
  `lsd_service_port` int(11) DEFAULT NULL COMMENT '上报服务的端口号',
  `lsd_last_report_time` timestamp NULL DEFAULT NULL COMMENT '最后一次上报时间，每次上报更新',
  `lsd_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '首次上报时创建时间',
  PRIMARY KEY (`lsd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='上报日志的客户端服务详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logging_service_details`
--

LOCK TABLES `logging_service_details` WRITE;
/*!40000 ALTER TABLE `logging_service_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `logging_service_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-23 16:15:54
