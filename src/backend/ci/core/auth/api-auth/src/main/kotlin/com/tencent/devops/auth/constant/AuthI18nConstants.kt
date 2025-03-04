package com.tencent.devops.auth.constant

object AuthI18nConstants {
    const val RESOURCE_TYPE_NAME_SUFFIX = ".resourceType.name"
    const val RESOURCE_TYPE_DESC_SUFFIX = ".resourceType.desc"
    const val AUTH_RESOURCE_GROUP_CONFIG_GROUP_NAME_SUFFIX = ".authResourceGroupConfig.groupName"
    const val AUTH_RESOURCE_GROUP_CONFIG_DESCRIPTION_SUFFIX = ".authResourceGroupConfig.description"
    const val ACTION_NAME_SUFFIX = ".actionName"
    const val BK_AGREE_RENEW = "bkAgreeRenew" // 同意续期
    const val BK_YOU_AGREE_RENEW = "bkYouAgreeRenew" // 你已选择同意用户续期
    const val BK_REFUSE_RENEW = "bkRefuseRenew" // 拒绝续期
    const val BK_YOU_REFUSE_RENEW = "bkYouRefuseRenew" // 你已选择拒绝用户续期
    const val BK_ALL_PROJECT_MEMBERS_GROUP = "bkAllProjectMembersGroup" // 全部项目成员组

    // **蓝盾超级管理员权限续期申请审批**\n申请人：{0}\n授权名称：{1}\n授权详情：{2}\n用户权限过期时间：{3}\n请选择是否同意用户续期权限\n
    const val BK_WEWORK_ROBOT_NOTIFY_MESSAGE = "bkWeworkRobotNotifyMessage"
    const val BK_APPROVER_AGREE_RENEW = "bkApproverAgreeRenew" // 审批人同意了您的权限续期
    const val BK_APPROVER_REFUSE_RENEW = "bkApproverRefuseRenew" // 审批人拒绝了您的权限续期
    const val BK_ADMINISTRATOR_NOT_EXPIRED = "bkAdministratorNotExpired" // 权限还未过期，不需要操作！
    const val BK_AUTHORIZATION_SUCCEEDED = "bkAuthorizationSucceeded" // 授权成功, 获取管理员权限120分钟
    const val BK_CANCELLED_AUTHORIZATION_SUCCEEDED = "bkCancelledAuthorizationSucceeded"

    // 取消授权成功, 缓存在5分钟后完全失效
    const val BK_FAILED_CALL_CALLBACK_API = "bkFailedCallCallbackApi" // 调用回调接口失败
    const val BK_CREATE_BKCI_PROJECT_APPLICATION = "bkCreateBkciProjectApplication" // 创建蓝盾项目{0}申请
    const val BK_REVISE_BKCI_PROJECT_APPLICATION = "bkReviseBkciProjectApplication" // 修改蓝盾项目{0}申请
    const val BK_PROJECT_NAME = "bkProjectName" // 项目名称
    const val BK_BELONG_PROJECT_NAME = "bkBelongProjectName" // 所属项目
    const val BK_PROJECT_ID = "bkProjectId" // 项目ID
    const val BK_PROJECT_DESC = "bkProjectDesc" // 项目描述
    const val BK_ORGANIZATION = "bkOrganization" // 所属组织
    const val BK_PROJECT_PRODUCT = "bkProjectProduct" // 项目所属运营产品
    const val BK_AUTH_SECRECY = "bkAuthSecrecy" // 项目性质
    const val BK_SUBJECT_SCOPES = "bkSubjectScopes" // 最大可授权人员范围
    const val BK_RESOURCE_TYPE_NAME = "bkResourceTypeName" // 资源类型名称
    const val BK_RESOURCE_NAME = "bkResourceName" // 资源名称
    const val BK_GROUP_NAME = "bkGroupName" // 用户组名称
    const val BK_VALIDITY_PERIOD = "bkValidityPeriod" // 申请期限
    const val BK_CREATE_PROJECT_APPROVAL = "bkCreateProjectApproval" // 创建项目{0}审批
    const val BK_UPDATE_PROJECT_APPROVAL = "bkUpdateProjectApproval" // 修改项目{0}审批
    const val BK_APPLY_TO_JOIN_PROJECT = "bkApplyToJoinProject" // 申请加入项目
    const val BK_APPLY_TO_JOIN_GROUP = "bkApplyToJoinGroup" // 申请加入用户组
    const val BK_DAY = "bkDay" // days
    const val BK_DEVOPS_NAME = "bkDevopsName" // 蓝盾名称
    const val BK_MONITOR_NAME = "bkMonitorName" // 监控平台名称
    const val BK_MONITOR_SPACE = "bkMonitorSpace" // 监控空间
    const val BK_MEMBER_EXPIRED_AT_DISPLAY_EXPIRED = "bkMemberExpiredAtDisplayExpired" // 有效期: 已过期
    const val BK_MEMBER_EXPIRED_AT_DISPLAY_NORMAL = "bkMemberExpiredAtDisplayNormal" // 有效期: {0}天
    const val BK_MEMBER_EXPIRED_AT_DISPLAY_PERMANENT = "bkMemberExpiredAtDisplayPermanent" // 有效期: 永久

    const val BK_APPLY_TO_HANDOVER = "bkApplyToHandover" // 申请移交
    const val BK_HANDOVER_GROUPS = "bkHandoverGroups" // 个权限用户组
    const val BK_HANDOVER_AUTHORIZATIONS = "bkHandoverAuthorizations" // 个授权
    const val BK_PROJECT = "bk_project" // 蓝盾项目
}
