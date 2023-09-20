import request from '@/utils/request'
import qs from 'qs'

export const studentGet = (data) => request.get('/student',{
    params:data
})

export const studentAdd = (data) => request.post('/student',data)

export const studentById = (id) => request.get('/student/'+id)

export const studentEdit = (data) => request.put('/student',data)

export const studentDelete = (ids) => request.delete('/student',{
    params: { ids },
    paramsSerializer: params => qs.stringify(params,{indices: false})
})