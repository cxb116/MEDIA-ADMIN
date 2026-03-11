<template>
  <ArtSearchBar
    ref="searchBarRef"
    v-model="formData"
    :items="formItems"
    @reset="handleReset"
    @search="handleSearch"
  />
</template>

<script setup lang="ts">
  interface Props {
    modelValue: Record<string, any>
  }
  interface Emits {
    (e: 'update:modelValue', value: Record<string, any>): void
    (e: 'search', params: Record<string, any>): void
    (e: 'reset'): void
  }
  const props = defineProps<Props>()
  const emit = defineEmits<Emits>()

  const searchBarRef = ref()
  const formData = computed({
    get: () => props.modelValue,
    set: (val) => emit('update:modelValue', val)
  })

  const enableOptions = ref([
    { label: '正常', value: 1 },
    { label: '禁用', value: 0 },
    { label: '审核中', value: 2 },
    { label: '拒绝', value: 3 }
  ])

  const formItems = computed(() => [
    {
      label: '广告位名称',
      key: 'name',
      type: 'input',
      placeholder: '请输入广告位名称',
      clearable: true
    },
    {
      label: '状态',
      key: 'enable',
      type: 'select',
      props: {
        placeholder: '请选择状态',
        options: enableOptions.value,
        clearable: true
      }
    }
  ])

  function handleReset() {
    emit('reset')
  }

  async function handleSearch() {
    emit('search', formData.value)
  }
</script>
