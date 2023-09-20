import { createPinia } from 'pinia'

const pinina=createPinia()

export default pinina

// import { useUserStore } from './modules/user'
// export { useUserStore }
export * from './modules/user'

