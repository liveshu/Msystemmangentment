<script setup>
import { ref } from 'vue'
import { dormAddManagement,dormEditManagement } from '@/api/dorm.js'
const dialogVisible = ref(false)
const formModel = ref({
    floorName:'',
    dormNumber:'',
    descrpition:''
})
const rules = {
    floorName:[
        { required:true,message:'请输入楼层名字',trigger:'blur' },
        { pattern:/^\S{1,10}$/,message:'楼层名必须是 1-10 位的非空字符',trigger:'blur' }
    ],
    dormNumber:[
        { required:true,message:'请输入寝室号',trigger:'blur' },
        { pattern:/^\S{1,10}$/,message:'寝室号必须是 1-10 位的非空字符',trigger:'blur' }
    ]
}
const formRef = ref()
const emit = defineEmits(['success'])
const onSubmit = async () => {
    await formRef.value.validate()
    const isEdit = formModel.value.id
    if (isEdit) {
        await dormEditManagement(formModel.value)
        ElMessage.success('修改成功')
    } else {
        await dormAddManagement(formModel.value)
        ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    emit('success',isEdit)
}
//组件对外暴露一个方法
const open = (row) => {
    dialogVisible.value = true
    formModel.value = { ...row }
}
defineExpose({
    open
})
</script>

<template>
        <el-dialog v-model="dialogVisible" 
            :title="formModel.id ? '编辑寝室' : '添加寝室'"
            width="30%">
            <el-form ref="formRef" :model="formModel" :rules="rules" label-width="100px" style="padding-right: 30px;">
                <el-form-item label="楼层名字" prop="floorName">
                    <el-input v-model="formModel.floorName" placeholder="请输入楼层名字"></el-input>
                </el-form-item>
                <el-form-item label="寝室号" prop="dormNumber">
                    <el-input v-model="formModel.dormNumber" placeholder="请输入寝室号"></el-input>
                </el-form-item>
                <el-form-item label="寝室描述">
                    <el-input v-model="formModel.descrpition" 
                    placeholder="请输入寝室描述"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="onSubmit">
                        确认
                    </el-button>
                </span>
            </template>
        </el-dialog>
</template>