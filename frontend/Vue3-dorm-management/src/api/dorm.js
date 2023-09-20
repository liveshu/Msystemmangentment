import request from '@/utils/request'
import qs from 'qs'
//获取楼层
export const dormGetManage = (data) => request.get('/dorm',{
    params: data
})

export const dormAddManagement = (data) => request.post('/dorm',data)

export const dormById = (id) => request.get('/dorm/'+id)

export const dormEditManagement = (data) => request.put('/dorm',data)

export const dormDeleteManagement = (ids) => request.delete('/dorm',{
    params: {ids},
    paramsSerializer: params => qs.stringify(params,{indices: false})
})