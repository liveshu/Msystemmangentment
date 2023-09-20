<script setup>
import {
  Management,
  Promotion,
  List,
  User,
  SwitchButton,
  CaretBottom
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
import { useUserStore } from '@/stores'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()


const handleCommand = async (key) => {
  if (key === 'logout') {
    // 退出操作
    await ElMessageBox.confirm('你确认要进行退出么', '温馨提示', {
      type: 'warning',
      confirmButtonText: '确认',
      cancelButtonText: '取消'
    })

    // 清除本地的数据 (token + user信息)
    userStore.removeToken()
    userStore.setUserInfo()
    router.push('/login')
  } else {
    // 跳转操作
    router.push(`/building/${key}`)
  }
}
</script>

<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu
        active-text-color="#ffd04b"
        background-color="#232323"
        :default-active="$route.path"
        text-color="#fff"
        router
      >
        <el-menu-item index="/building/floor">
          <el-icon><Promotion /></el-icon>
          <span>楼层管理</span>
        </el-menu-item>
        
        <el-menu-item index="/building/dorm">
          <el-icon><Management /></el-icon>
          <span>寝室管理</span>
        </el-menu-item>
        
        <el-menu-item index="/building/student">
          <el-icon><List /></el-icon>
          <span>学生管理</span>
        </el-menu-item>

        <el-menu-item index="/building/admin">
          <el-icon><User /></el-icon>
          <span>添加管理员</span>
        </el-menu-item> 
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div>
          管理员：<strong>{{ userStore.getUserInfo().userName }}</strong>
        </div>
        <el-dropdown placement="bottom-end" @command="handleCommand">
          <span class="el-dropdown__box">
            <el-avatar :src="avatar" />
            <el-icon><CaretBottom /></el-icon>
          </span>
          <!-- 折叠的下拉部分 -->
          <template #dropdown>
              <el-dropdown-item command="logout" :icon="SwitchButton"
                >退出登录</el-dropdown-item>
              <el-dropdown-item command="admin" :icon="User">添加管理员</el-dropdown-item>
          </template>
        </el-dropdown>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
      <el-footer>宿舍管理系统 ©2023 Created </el-footer>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;
  .el-aside {
    background-color: #232323;
    .el-menu {
      border-right: none;
    }
  }
  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .el-dropdown__box {
      display: flex;
      align-items: center;
      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }
  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>