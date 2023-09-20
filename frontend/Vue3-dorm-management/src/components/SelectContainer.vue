<script setup>
import { floorGetManagement } from '@/api/floor.js'
import { ref } from 'vue'

defineProps({
    modelValue: {
        type: String
    }
})

const emit = defineEmits(['update:modelValue'])
const List = ref([])

const getList = async () =>{
    const res =await  floorGetManagement()
    List.value = res.data.data
}
getList()
</script>

<template>
    <el-select :modelValue="modelValue" 
    @update:modelValue="emit('update:modelValue',$event)">
        <el-option v-for="item in List" :key="item.id"
        :label="item.floorName" :value="item.floorName"></el-option>

    </el-select>

</template>