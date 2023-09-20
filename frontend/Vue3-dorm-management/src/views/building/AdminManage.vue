<script setup>
import { ref } from 'vue'
import { userRegister } from '@/api/user.js'
const userForm = ref()
const user = ref({
    name: '',
    username:'',
    password: '',
    passswordRepeat: ''
})
const checkPassword = (rule, value, callback) => {
  if (value !== user.value.password) {
    callback(new Error('两个密码不一致'))
  } else {
    callback()
  }
}
const rules = ref({
    name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在2-20之间', trigger: 'blur' },
    ],
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 3, max: 10, message: '长度在5-10之间', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 15, message: '确认密码长度在6-15位之间', trigger: 'blur' }
    ],
    passswordRepeat: [
        { required: true, message: '请再次输入密码', trigger: 'blur' },
        { min: 6, max: 15, message: '确认密码长度在6-15位之间', trigger: 'blur' },
        { validator: checkPassword, trigger: 'blur' }
    ]
})

const userReset = (form) => {
    form.resetFields()
}
const userAdd = async () => {
    await userForm.value.validate()
    await userRegister(user.value)
    ElMessage.success('添加管理员成功')
    userForm.value.resetFields()
}
</script>

<template>
    <page-container title="添加管理员">
        <el-row>
            <el-col :span="10" :offset="6">
                <el-form
                    ref="userForm"
                    label-width="100px"
                    :model="user"
                    :rules="rules"
                >
                    <el-form-item label="姓名" prop="name">
                        <el-input v-model="user.name"></el-input>
                    </el-form-item>
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="user.username"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="user.password" type="password"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="passswordRepeat">
                        <el-input v-model="user.passswordRepeat" type="password"></el-input>
                    </el-form-item>
                    <el-form-item style="margin-left: 60px;">
                        <el-button type="primary" @click="userAdd">确认添加</el-button>
                        <el-button @click="userReset(userForm)">重置表单</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </page-container>
</template>