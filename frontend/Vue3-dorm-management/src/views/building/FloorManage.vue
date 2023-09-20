<script setup>
import { ref } from 'vue'
import { Edit,Delete } from '@element-plus/icons-vue'
import { floorGetManagement,floorById,floorDeleteManagement } from '@/api/floor.js'
import FloorEdit from './components/FloorEdit.vue'

const floorList = ref([])

const loading = ref(false)


const getFloorList = async () => {
    loading.value = true
    const res = await floorGetManagement()
    floorList.value = res.data.data
    loading.value = false
}
getFloorList()

const dialog = ref()

const onAdd = () => {
    dialog.value.open()
}

const onEdit = async (id) => {
    const res = await floorById(id)
    const row = res.data.data
    dialog.value.open(row)
}

const onDelte = (row) => {
    ElMessageBox.confirm('你确认要删除该楼层吗？','温馨提示',{
        type: 'warning',
        confirmButtonText: '确认',
        cancelButtonText:'取消'
    }) 
    .then(async () => {
    await floorDeleteManagement(row.id)
    ElMessage.success('删除成功')
    getFloorList()
    })
    .catch(() => {
        ElMessage.info('删除取消')
    })

}

const onSuccess = () => {
    getFloorList()
}

</script>

<template>
    <page-container title="楼层管理">
        <template #extra>
            <el-button @click="onAdd">添加楼层</el-button>
        </template>

        <el-table v-loading="loading" :data="floorList" border style="width=100%">
            <el-table-column type="index" label="序号" width="100"></el-table-column>
            <el-table-column prop="floorName" label="楼层名字"></el-table-column>
            <el-table-column prop="number" label="楼层寝室数量"></el-table-column>
            <el-table-column prop="updateTime" label="更新时间"></el-table-column>
            <el-table-column prop="updateUser" label="更新人"></el-table-column>
            <el-table-column label="操作" width="150">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle 
                    type="primary" plain
                    @click="onEdit(row.id)"></el-button>
                    <el-button :icon="Delete" circle type="danger" plain
                    @click="onDelte(row)"></el-button>
                </template>
            </el-table-column>

            <template #empty>
                <el-empty description="没有数据"></el-empty>
            </template>
        </el-table>

        <floor-edit ref="dialog" @success="onSuccess"></floor-edit>
    </page-container>
</template>