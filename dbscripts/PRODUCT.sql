/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : PRODUCT

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 27/11/2018 14:19:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stock` int(11) NULL DEFAULT 10000000,
  `merchant_id` bigint(20) NULL DEFAULT NULL COMMENT '商户id',
  `price` decimal(10, 0) NULL DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, 'iphone4', 1000000000, 1, 1000);
INSERT INTO `product` VALUES (2, 'ihpne4s', 1000000000, 1, 1100);
INSERT INTO `product` VALUES (3, 'iphone5', 1000000000, 1, 2000);
INSERT INTO `product` VALUES (4, 'iphone5s', 1000000000, 2, 2100);
INSERT INTO `product` VALUES (5, 'iphone6', 1000000000, 2, 2500);
INSERT INTO `product` VALUES (6, 'iphone6s', 1000000000, 1, 3000);
INSERT INTO `product` VALUES (7, 'iphone7', 1000000000, 2, 3800);
INSERT INTO `product` VALUES (8, 'iphone8', 1000000000, 2, 4000);
INSERT INTO `product` VALUES (9, 'iphoneX', 1000000000, 2, 4500);

-- ----------------------------
-- Table structure for product_stock_history
-- ----------------------------
DROP TABLE IF EXISTS `product_stock_history`;
CREATE TABLE `product_stock_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL,
  `stock_action` int(11) NULL DEFAULT NULL COMMENT '库存操作  -1:减少    1:增加',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `quality` int(11) NULL DEFAULT NULL COMMENT '库存操作数量',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态 0:未生效    1:已生效',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_product_id_order_id`(`product_id`, `order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品库存历史表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
