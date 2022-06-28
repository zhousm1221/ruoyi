import request from '@/utils/request'

// 查询公告列表
export function listNotice(query) {
  return request({
    url: '/system/notice/list',
    method: 'get',
    params: query
  })
}

// 查询报表管理列表
export function getlistNotice(query) {
  return request({
    url: '/system/model/list',
    method: 'get',
    params: query
  })
}

// 新增excel模版
export function addExcelData(data) {
  return request({
    url: '/system/model',
    method: 'post',
    data: data
  })
}

// 查询报表模版
export function aboutList(query) {
  return request({
    url: '/system/model/list',
    method: 'get',
    params: query
  })
}

// 查看作业完成情况
export function instTask(instID) {
  return request({
    url: '/system/inst/task/' + instID,
    method: 'get'
  })
}

// 查看作业下发详情
export function instJob(jobID) {
  return request({
    url: '/system/inst/job/' + jobID,
    method: 'get'
  })
}

// 提交待办任务
export function dealtTask(data) {
  return request({
    url: '/system/center',
    method: 'put',
    data: data
  })
}

// 查看模版详情
export function modeDetails(modelId) {
  return request({
    url: 'system/model/' + modelId,
    method: 'get'
  })
}

// 人员下发列表
export function issuedList(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}

// 确定下发按钮
export function confirmToIssue(data) {
  return request({
    url: '/system/inst/issued',
    method: 'post',
    data: data
  })
}

// 下发作业列表
export function instList(query) {
  return request({
    url: '/system/inst/list',
    method: 'get',
    params: query
  })
}

// 查看待办列表
export function dealtWithList(query) {
  return request({
    url: '/system/center/list',
    method: 'get',
    params: query
  })
}

// 查看已办列表
export function doneList(query) {
  return request({
    url: '/system/center/list',
    method: 'get',
    params: query
  })
}

// 查看已办任务详情
export function taskDetails(id) {
  return request({
    url: '/system/center/' + id + '/1',
    method: 'get'
  })
}

// 查看待办任务详情
export function toDODetails(id) {
  return request({
    url: '/system/center/' + id + '/0',
    method: 'get'
  })
}

// 查询公告详细
export function getNotice(noticeId) {
  return request({
    url: '/system/notice/' + noticeId,
    method: 'get'
  })
}

// 新增公告
export function addNotice(data) {
  return request({
    url: '/system/notice',
    method: 'post',
    data: data
  })
}

// 修改公告
export function updateNotice(data) {
  return request({
    url: '/system/notice',
    method: 'put',
    data: data
  })
}

// 删除公告
export function delNotice(noticeId) {
  return request({
    url: '/system/notice/' + noticeId,
    method: 'delete'
  })
}
