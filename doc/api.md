
 <h1 class="curproject-name"> true </h1> 
 


# 公共分类

# task-controller

## 抢任务
<a id=抢任务> </a>
### 基本信息

**Path：** /task/holdTask

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
| Token  |   | 是  |   |  Token |
| Agent  |   | 是  |   |  Agent |
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| taskDetailId | 是  |   |  任务详细ID |
**Body**

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody">
               </tbody>
              </table>
            
### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>integer</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5><p key=2><span style="font-weight: '700'">format: </span><span>int32</span></p></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr>
               </tbody>
              </table>
            
## 提交任务
<a id=提交任务> </a>
### 基本信息

**Path：** /task/commitUserTask

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| taskId | 是  |   |  任务Id |
| commitAddress | 否  |   |  提交地址 |
| remark | 否  |   |  备注 |

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>integer</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5><p key=2><span style="font-weight: '700'">format: </span><span>int32</span></p></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr>
               </tbody>
              </table>
            
## 获取我的任务列表
<a id=获取我的任务列表> </a>
### 基本信息

**Path：** /task/getUserTaskList

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| taskStatus | 否  |   |  任务状态(0-进行中，1-已完成) |
**Body**

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody">
               </tbody>
              </table>
            
### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-0><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> taskTotal</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>任务总数</span></td><td key=5></td></tr><tr key=0-2-1><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> taskingTotal</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>正在进行中任务数</span></td><td key=5></td></tr><tr key=0-2-2><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> taskComplateTolal</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>完成任务数</span></td><td key=5></td></tr><tr key=0-2-3><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> taskList</span></td><td key=1><span>object []</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5><p key=3><span style="font-weight: '700'">item 类型: </span><span>object</span></p></td></tr><tr key=0-2-3-0><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> id</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>任务ID</span></td><td key=5></td></tr><tr key=0-2-3-1><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> name</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>任务名称</span></td><td key=5></td></tr><tr key=0-2-3-2><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> level</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>任务等级(A,B,C,D)</span></td><td key=5></td></tr><tr key=0-2-3-3><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> category</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>任务类型(0-个人，1-团队)</span></td><td key=5></td></tr><tr key=0-2-3-4><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> startDateTime</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>开始日期</span></td><td key=5></td></tr><tr key=0-2-3-5><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> endDateTime</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>结束日期</span></td><td key=5></td></tr><tr key=0-2-3-6><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> rewardType</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>奖励类型(1-true,2-ttr,3-rmp)</span></td><td key=5></td></tr><tr key=0-2-3-7><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> rewardNum</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>奖励数量</span></td><td key=5></td></tr><tr key=0-2-3-8><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> pushAddress</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>提交地址</span></td><td key=5></td></tr><tr key=0-2-3-9><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> description</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>描述</span></td><td key=5></td></tr>
               </tbody>
              </table>
            
## 获取我的任务详情
<a id=获取我的任务详情> </a>
### 基本信息

**Path：** /task/getUserTaskInfo

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| taskId | 是  |   |  taskId |
**Body**

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-0><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> task</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-0-0><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> id</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>ID</span></td><td key=5></td></tr><tr key=0-2-0-1><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> name</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>任务名</span></td><td key=5></td></tr><tr key=0-2-0-2><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> level</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>任务等级(A,B,C,D)</span></td><td key=5></td></tr><tr key=0-2-0-3><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> category</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>任务类型(0-个人,1-团队)</span></td><td key=5></td></tr><tr key=0-2-0-4><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> startDateTime</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>开始时间</span></td><td key=5></td></tr><tr key=0-2-0-5><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> endDateTime</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>结束时间</span></td><td key=5></td></tr><tr key=0-2-0-6><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> rewardType</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>奖励类型(1-true,2-ttr,3-rmp)</span></td><td key=5></td></tr><tr key=0-2-0-7><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> rewardNum</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>奖励数量</span></td><td key=5></td></tr><tr key=0-2-0-8><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> pushAddress</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>提交地址</span></td><td key=5></td></tr><tr key=0-2-0-9><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> description</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>描述</span></td><td key=5></td></tr><tr key=0-2-1><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> taskCompleteInfo</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-1-0><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> id</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-1-1><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> auditResult</span></td><td key=1><span>null</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>审核结果</span></td><td key=5></td></tr><tr key=0-2-1-2><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> auditStatus</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>审核状态(0-未审核,1-审核,2-已奖励)</span></td><td key=5></td></tr><tr key=0-2-1-3><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> pushAddress</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>提交地址</span></td><td key=5></td></tr><tr key=0-2-1-4><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> remark</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>说明</span></td><td key=5></td></tr><tr key=0-2-1-5><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> reward</span></td><td key=1><span>null</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>奖励收入</span></td><td key=5></td></tr>
               </tbody>
              </table>
            
### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>integer</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5><p key=2><span style="font-weight: '700'">format: </span><span>int32</span></p></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr>
               </tbody>
              </table>
            
# un-auth-controller

## 获取任务数据
<a id=获取任务数据> </a>
### 基本信息

**Path：** /task/unauth/getTaskPage

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| taskName | 否  |   |  taskName |
| category | 否  |   |  category |
| level | 否  |   |  level |
| reward | 否  |   |  reward |
| pageIndex | 是  |   |  pageIndex |
| pageSize | 是  |   |  pageSize |

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-0><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> content</span></td><td key=1><span>object []</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5><p key=3><span style="font-weight: '700'">item 类型: </span><span>object</span></p></td></tr><tr key=0-2-0-0><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> id</span></td><td key=1><span>number</span></td><td key=2>必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-0-1><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> name</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span>任务名称</span></td><td key=5></td></tr><tr key=0-2-0-2><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> level</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span>任务等级,A,B,C,D</span></td><td key=5></td></tr><tr key=0-2-0-3><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> category</span></td><td key=1><span>number</span></td><td key=2>必须</td><td key=3></td><td key=4><span>任务类型(0-个人，1-团队)</span></td><td key=5></td></tr><tr key=0-2-0-4><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> startDateTime</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span>开始日期</span></td><td key=5></td></tr><tr key=0-2-0-5><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> endDateTime</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span>结束日期</span></td><td key=5></td></tr><tr key=0-2-0-6><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> rewardType</span></td><td key=1><span>number</span></td><td key=2>必须</td><td key=3></td><td key=4><span>奖励类型(1-true,2-ttr,3-rmp)</span></td><td key=5></td></tr><tr key=0-2-0-7><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> rewardNum</span></td><td key=1><span>number</span></td><td key=2>必须</td><td key=3></td><td key=4><span>奖励数量</span></td><td key=5></td></tr><tr key=0-2-0-8><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> pushAddress</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span>提交地址</span></td><td key=5></td></tr><tr key=0-2-0-9><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> description</span></td><td key=1><span>string</span></td><td key=2>必须</td><td key=3></td><td key=4><span>描述</span></td><td key=5></td></tr><tr key=0-2-1><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> totalPages</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>总页数</span></td><td key=5></td></tr><tr key=0-2-2><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> totalElements</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>总数</span></td><td key=5></td></tr><tr key=0-2-3><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> last</span></td><td key=1><span>boolean</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-4><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> number</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-5><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> size</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-6><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> sort</span></td><td key=1><span>null</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-7><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> numberOfElements</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-8><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> first</span></td><td key=1><span>boolean</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr>
               </tbody>
              </table>
            
## 获取任务明细
<a id=获取任务明细> </a>
### 基本信息

**Path：** /task/unauth/getTaskInfo

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| taskId | 是  |   |  任务ID |

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-0><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> task</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-0-0><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> id</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-0-1><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> name</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>任务名称</span></td><td key=5></td></tr><tr key=0-2-0-2><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> level</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>任务等级,A,B,C,D</span></td><td key=5></td></tr><tr key=0-2-0-3><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> category</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>任务类型(0-个人，1-团队)</span></td><td key=5></td></tr><tr key=0-2-0-4><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> startDateTime</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>开始日期</span></td><td key=5></td></tr><tr key=0-2-0-5><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> endDateTime</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>结束日期</span></td><td key=5></td></tr><tr key=0-2-0-6><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> rewardType</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>奖励类型(1-true,2-ttr,3-rmp)</span></td><td key=5></td></tr><tr key=0-2-0-7><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> rewardNum</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>奖励数量</span></td><td key=5></td></tr><tr key=0-2-0-8><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> pushAddress</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>提交地址</span></td><td key=5></td></tr><tr key=0-2-0-9><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> description</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>描述</span></td><td key=5></td></tr><tr key=0-2-1><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> taskDetailList</span></td><td key=1><span>object []</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5><p key=3><span style="font-weight: '700'">item 类型: </span><span>object</span></p></td></tr><tr key=0-2-1-0><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> id</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-1-1><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> station</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>岗位</span></td><td key=5></td></tr><tr key=0-2-1-2><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> peopleNum</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>人数</span></td><td key=5></td></tr><tr key=0-2-1-3><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> reward</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>奖励</span></td><td key=5></td></tr><tr key=0-2-1-4><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> hasPeople</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>已报名人数</span></td><td key=5></td></tr>
               </tbody>
              </table>
            
# user-controller

## 获取个人信息
<a id=获取个人信息> </a>
### 基本信息

**Path：** /user/getUserInfo

**Method：** GET

**接口描述：**


### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| rewardType | 是  |   |  奖励类型(1-true,2-ttr,3-rmp) |

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-0><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> user</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-0-0><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> id</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-0-1><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> personName</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>名称</span></td><td key=5></td></tr><tr key=0-2-0-2><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> level</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>等级</span></td><td key=5></td></tr><tr key=0-2-1><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> recommendPeople</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>推荐人数</span></td><td key=5></td></tr><tr key=0-2-2><td key=0><span style="padding-left: 20px"><span style="color: #8c8a8a">├─</span> userAccount</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-2-0><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> trueReward</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-2-1><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> ttrReward</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-2-2><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> gitReward</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2-2-3><td key=0><span style="padding-left: 40px"><span style="color: #8c8a8a">├─</span> accountDetails</span></td><td key=1><span>object []</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5><p key=3><span style="font-weight: '700'">item 类型: </span><span>object</span></p></td></tr><tr key=0-2-2-3-0><td key=0><span style="padding-left: 60px"><span style="color: #8c8a8a">├─</span> rewardType</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>奖励类型(1-true,2-ttr,3-rmp)</span></td><td key=5></td></tr><tr key=0-2-2-3-1><td key=0><span style="padding-left: 60px"><span style="color: #8c8a8a">├─</span> rewardResource</span></td><td key=1><span>number</span></td><td key=2>非必须</td><td key=3></td><td key=4><span>奖励来源(1-任务,2-推荐)</span></td><td key=5></td></tr><tr key=0-2-2-3-2><td key=0><span style="padding-left: 60px"><span style="color: #8c8a8a">├─</span> reward</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr>
               </tbody>
              </table>
            
# account-controller

## getDeclare
<a id=getDeclare> </a>
### 基本信息

**Path：** /unauth/account/getDeclare

**Method：** POST

**接口描述：**
undefined

### 请求参数

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>integer</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5><p key=2><span style="font-weight: '700'">format: </span><span>int32</span></p></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr>
               </tbody>
              </table>
            
## getVerifyCode
<a id=getVerifyCode> </a>
### 基本信息

**Path：** /unauth/account/verifyCode/{mobile}

**Method：** GET

**接口描述：**
undefined

### 请求参数
**路径参数**
| 参数名称 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| mobile |   |  mobile |

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>integer</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5><p key=2><span style="font-weight: '700'">format: </span><span>int32</span></p></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr>
               </tbody>
              </table>
            
## login
<a id=login> </a>
### 基本信息

**Path：** /unauth/account/login

**Method：** POST

**接口描述：**
undefined

### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| mobile | 是  |   |  mobile |
| verifyCode | 是  |   |  verifyCode |

### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>integer</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5><p key=2><span style="font-weight: '700'">format: </span><span>int32</span></p></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr>
               </tbody>
              </table>
            
## register
<a id=register> </a>
### 基本信息

**Path：** /unauth/account/register

**Method：** POST

**接口描述：**
undefined

### 请求参数
**Query**

| 参数名称  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ |
| name | 是  |   |  name |
| wxNickName | 是  |   |  wxNickName |
| wxNum | 是  |   |  wxNum |
| openId | 否  |   |  openId |
| trueChainAddress | 是  |   |  trueChainAddress |
| mobile | 是  |   |  mobile |
| verifyCode | 是  |   |  verifyCode |
**Body**

| 参数名称  | 参数类型  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| file | file  |  是 |    |  file |



### 返回数据

<table>
  <thead class="ant-table-thead">
    <tr>
      <th key=name>名称</th><th key=type>类型</th><th key=required>是否必须</th><th key=default>默认值</th><th key=desc>备注</th><th key=sub>其他信息</th>
    </tr>
  </thead><tbody className="ant-table-tbody"><tr key=0-0><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> code</span></td><td key=1><span>integer</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5><p key=2><span style="font-weight: '700'">format: </span><span>int32</span></p></td></tr><tr key=0-1><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> message</span></td><td key=1><span>string</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr><tr key=0-2><td key=0><span style="padding-left: 0px"><span style="color: #8c8a8a"></span> result</span></td><td key=1><span>object</span></td><td key=2>非必须</td><td key=3></td><td key=4><span></span></td><td key=5></td></tr>
               </tbody>
              </table>
            
