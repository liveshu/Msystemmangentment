import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import pinina from '@/stores/index'
import '@/assets/main.scss'

const app = createApp(App)

app.use(pinina)
app.use(router)

app.mount('#app')
