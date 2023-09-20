import request from '@/utils/request'

export const floorGetManagement= () => request.get('/floor')

export const floorById = (id) => request.get('/floor/'+id)

export const floorAddManagement = (data) => request.post('/floor',data)

export const floorEditManagement = (data) => request.put('/floor',data)

export const floorDeleteManagement = (id)=> request.delete('/floor/'+id)

