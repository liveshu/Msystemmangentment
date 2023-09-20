<script setup>
import { ref } from 'vue'
import DormEdit from './components/DormEdit.vue';
import { Edit,Delete } from '@element-plus/icons-vue'
import { dormGetManage,dormDeleteManagement,dormById } from '@/api/dorm';

const dormList = ref([])

const total = ref(0)

const params = ref({
    page: 1,
    pageSize: 5,
    floorName: '',
    dormNumber: ''
})
const sel = ref([])
const loading = ref(false)
const dialog = ref()
const dormTable =ref()
const getDormList = async () => {
    loading.value = true
    const res = await dormGetManage(params.value)
    total.value = res.data.data.total
    dormList.value = res.data.data.records
    loading.value = false
}
getDormList()

const onSizeChange = (size) => {
    params.value.page = 1
    params.value.pageSize = size
    getDormList()
}
const onCurrentChange = (page) => {
    params.value.page = page
    getDormList()
}

const onSearch = () => {
    params.value.page = 1
    getDormList()
}
const onReset = () => {
    params.value.floorName = ''
    params.value.dormNumber = ''
    params.value.page = 1
    getDormList()
}

const onAdd = () => {
    dialog.value.open()
}
const onEdit = async (row) => {
    const res = await dormById(row.id)
    dialog.value.open(res.data.data)
}
//删除单条数据
const onDelte = async (row) => {

    ElMessageBox.confirm('你确认要删除这条数据吗？','温馨提示',{
        type: 'warning',
        confirmButtonText: '确认',
        cancelButtonText:'取消'
    }) 
    .then(async () => {
        const id= [row.id]
        await dormDeleteManagement(id)
        ElMessage.success('删除成功')
        getDormList()
    })
    .catch(() => {
        ElMessage.info('删除取消')
    })
}
//批量删除
const batchDelete = async () => {
    if (sel.value.length !== 0){
        ElMessageBox.confirm('你确认要删除这些数据吗？','温馨提示',{
            type: 'warning',
            confirmButtonText: '确认',
            cancelButtonText:'取消'
        }) 
        .then(async () => {
            await dormDeleteManagement(sel.value)
            ElMessage.success('删除成功')
            dormTable.value.clearSelection()
            sel.value = []
            getDormList()
        })
        .catch(() => {
            ElMessage.info('删除取消')
        })}else{
            ElMessage.error('未选择数据')
    }
}

const handleSelectionChange = sels => 
    sel.value = sels.map((item) => item.id)


const getRowKeys = (row) => {
    return row.id
}
const onSuccess = (isEdit) => {
    if (isEdit){
        getDormList()
    }else {
        const lastPage = Math.ceil((total.value + 1) / params.value.pageSize)
        params.value.page = lastPage
        getDormList()
    }
    
}
</script>

<template>
    <page-container title="寝室管理">
        <template #extra>
            <el-button @click="onAdd">添加寝室</el-button>
        </template>

        <el-form inline>
            <el-form-item label="楼层名:">
                <select-container v-model="params.floorName"></select-container>
            </el-form-item>
            <el-form-item label="寝室号:">
                <el-input v-model="params.dormNumber" placeholder="输入寝室号"></el-input>
            </el-form-item>
        <el-form-item>
            <el-button @click="onSearch" type="primary">搜索</el-button>
            <el-button @click="onReset">重置</el-button>
            <el-button @click="batchDelete" type="danger"
            :disabled="sel.length === 0">批量删除</el-button>
        </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="dormList" border style="width=100%"
            ref="dormTable"
            @selection-change="handleSelectionChange"
            :row-key="getRowKeys"
        >
            <el-table-column type="selection" :reserve-selection="true"></el-table-column>
            <el-table-column type="index" label="序号" width="70"></el-table-column>
            <el-table-column prop="floorName" label="楼层名字"></el-table-column>
            <el-table-column prop="dormNumber" label="寝室号"></el-table-column>
            <el-table-column prop="descrpition" label="寝室描述"></el-table-column>
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

        <dorm-edit ref="dialog" @success="onSuccess"></dorm-edit>
    </page-container>
</template>