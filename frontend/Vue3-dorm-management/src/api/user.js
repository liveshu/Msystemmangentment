import request from '@/utils/request'

export const userRegister = data => request.post('/signIn',data)

export const userLogin = data => request.post('/login',data)