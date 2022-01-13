/*
 * Tencent is pleased to support the open source community by making BK-CI 蓝鲸持续集成平台 available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * BK-CI 蓝鲸持续集成平台 is licensed under the MIT license.
 *
 * A copy of the MIT License is included in this file.
 *
 *
 * Terms of the MIT License:
 * ---------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tencent.devops.project.service

import com.tencent.devops.common.api.pojo.ShardingRoutingRule

interface ShardingRoutingRuleService {

    /**
     * 添加分片路由规则
     * @param userId 用户ID
     * @param shardingRoutingRule 片路由规则
     * @return 布尔值
     */
    fun addShardingRoutingRule(userId: String, shardingRoutingRule: ShardingRoutingRule): Boolean

    /**
     * 删除分片路由规则
     * @param userId 用户ID
     * @param id 规则ID
     * @return 布尔值
     */
    fun deleteShardingRoutingRule(userId: String, id: String): Boolean

    /**
     * 更新分片路由规则
     * @param userId 用户ID
     * @param id 规则ID
     * @param shardingRoutingRule 片路由规则
     * @return 布尔值
     */
    fun updateShardingRoutingRule(userId: String, id: String, shardingRoutingRule: ShardingRoutingRule): Boolean

    /**
     * 根据ID查找分片路由规则
     * @param id 规则ID
     * @return 分片路由规则信息
     */
    fun getShardingRoutingRuleById(id: String): ShardingRoutingRule?

    /**
     * 根据规则名称查找分片路由规则
     * @param routingName 规则名称
     * @return 分片路由规则信息
     */
    fun getShardingRoutingRuleByName(routingName: String): ShardingRoutingRule?
}