import axios from 'axios'
import { useUserStore } from '@/stores'
import { ElMessage } from 'element-plus'
import router from '@/router';

const instance = axios.create({
    baseURL: '/api/admin',
    timeout: 5000,
  });

// 请求拦截器
instance.interceptors.request.use( 
    (config) => {
    // 在发送请求之前做些什么
    const useStore = useUserStore() 
    if (useStore.getToken()) {
        config.headers.token = useStore.getToken()
    }
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 响应拦截器
instance.interceptors.response.use(
     (res) => {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    if (res.data.code === 1) {
        return res
    }
    
    ElMessage.error( res.data.msg || '服务异常')
    return Promise.reject(res.data)

  }, 
  (err) =>  {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    if (err.response?.status === 401) {
        router.push('/login')
        ElMessage.error( err.response.data.mag || '登录异常')
    }else{
      ElMessage.error( err.response.data.mag || '服务异常')
    }

    
    return Promise.reject(err);
  });


  export default instance