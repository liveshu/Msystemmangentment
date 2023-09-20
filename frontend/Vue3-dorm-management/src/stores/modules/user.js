import { defineStore } from "pinia";

export const useUserStore = defineStore('drom-user',() => {
    const getToken = () => {
        return localStorage.getItem('token')
    }
    const setToken = (newToken) => {
        localStorage.setItem('token',newToken)
    }
    const removeToken = () => {
        localStorage.removeItem('token')
    }
    const getUserInfo = ()=> {
        return JSON.parse(localStorage.getItem('userInfo'))
    }
    const setUserInfo = (userInfo) => {
        userInfo? localStorage.setItem('userInfo',JSON.stringify(userInfo)) : localStorage.removeItem('userInfo')
    }
    return {
        getToken,
        setToken,
        removeToken,
        getUserInfo,
        setUserInfo
    }
})