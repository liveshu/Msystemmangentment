<script setup>
import { ref } from 'vue'
import { Edit,Delete } from '@element-plus/icons-vue'
import { studentGet,studentById,studentDelete } from '@/api/student.js'
import StudentEdit from './components/StudentEdit.vue'
const studentList = ref([])
const total = ref(0)
const params = ref({
    page: 1,
    pageSize: 5,
    dormNumber:'',
    student:''
})
const loading = ref(false)
const studentTable = ref()
const getStudentList = async () => {
    loading.value = true
    const res = await studentGet(params.value)
    total.value = res.data.data.total
    studentList.value = res.data.data.records
    loading.value = false
}
getStudentList()

const onSearch = () => {
    params.value.page = 1
    getStudentList()
}
const onReset = () => {
    console.log('点击了重置')
    params.value.dormNumber = ''
    params.value.student = ''
    params.value.page = 1
    getStudentList()
}
const onSizeChange = (size) => {
    params.value.page = 1
    params.value.pageSize = size
    getStudentList()
}
const onCurrentChange = (page) => {
    params.value.page = page
    getStudentList()
}

const studentDialog = ref() 
const onAdd = () => {
    studentDialog.value.open()
}

const onEdit = async (row) => {
    const res = await studentById(row.id)
    studentDialog.value.open(res.data.data)
}
const onSuccess = (isEdit) => {
    if (isEdit) {
        getStudentList()
    }else {
        const lastPage = Math.ceil((total.value + 1) / params.value.pageSize)
        params.value.page = lastPage
        getStudentList()
    }
}

const sel = ref([])
const handleSelectionChange = sels => 
    sel.value = sels.map((item) => item.id)

const getRowKeys = (row) => {
    return row.id
}   
const onDelte = async (row) => {
    ElMessageBox.confirm('你确认要删除这条数据吗？','温馨提示',{
        type: 'warning',
        confirmButtonText: '确认',
        cancelButtonText:'取消'
    }) 
    .then(async () => {
        const ids = [row.id]
        await studentDelete(ids)
        ElMessage.success('删除成功')
        getStudentList()
    })
    .catch(() => {
        ElMessage.info('删除取消')
    })
}

const batchDelete = async () => {
    ElMessageBox.confirm('你确认要删除这些数据吗？','温馨提示',{
        type: 'warning',
        confirmButtonText: '确认',
        cancelButtonText:'取消'
    }) 
    .then(async () => {
        await studentDelete(sel.value)
        ElMessage.success('删除成功')
        sel.value = []
        studentTable.value.clearSelection()
        getStudentList()
    })
    .catch(() => {
        ElMessage.info('删除取消')
    })
}
</script>

<template>
    <page-container title="学生管理">
        <template #extra>
            <el-button @click="onAdd">添加学生</el-button>
        </template>
        <el-form inline>
            <el-form-item label="寝室号:">
                <el-input v-model="params.dormNumber" placeholder="输入寝室号"></el-input>
            </el-form-item>
            <el-form-item label="学生姓名:">
                <el-input v-model="params.student" placeholder="输入学生姓名"></el-input>
            </el-form-item>
        <el-form-item>
            <el-button @click="onSearch" type="primary">搜索</el-button>
            <el-button @click="onReset">重置</el-button>
            <el-button @click="batchDelete" type="danger"
            :disabled="sel.length === 0">批量删除</el-button>
        </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="studentList" border style="width=100%"
            ref="studentTable"
            @selection-change="handleSelectionChange"
            :row-key="getRowKeys"
        >
            <el-table-column type="selection" :reserve-selection="true"></el-table-column>
            <el-table-column type="index" label="序号" width="70"></el-table-column>
            <el-table-column prop="dormNumber" label="寝室号"></el-table-column>
            <el-table-column prop="student" label="学生姓名"></el-table-column>
            <el-table-column prop="updateTime" label="更新时间" width="170"></el-table-column>
            <el-table-column prop="updateUser" label="更新人"></el-table-column>
            <el-table-column label="操作" width="150">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle 
                    type="primary" plain
                    @click="onEdit(row)"></el-button>
                    <el-button :icon="Delete" circle type="danger" plain
                    @click="onDelte(row)"></el-button>
                </template>
            </el-table-column>

            <template #empty>
                <el-empty description="没有数据"></el-empty>
            </template>
        </el-table>

        <el-pagination
            v-model:current-page="params.page"
            v-model:page-size="params.pageSize"
            :page-sizes="[2, 3, 5, 10]"
            :background="true"
            layout="jumper, total, sizes, prev, pager, next"
            :total="total"
            @size-change="onSizeChange"
            @current-change="onCurrentChange"
            style="margin-top: 20px; justify-content: flex-end;"
        />

        <student-edit ref="studentDialog" @success="onSuccess"></student-edit>

    </page-container>
</template>