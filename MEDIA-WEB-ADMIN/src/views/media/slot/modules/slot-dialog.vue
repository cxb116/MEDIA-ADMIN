<template>
  <ElDialog
    v-model="dialogVisible"
    :title="dialogType === 'add' ? '添加广告位' : '编辑广告位'"
    width="600px"
    align-center
  >
    <ElForm ref="formRef" :model="formData" :rules="rules" label-width="100px">
      <ElRow :gutter="20">
        <ElCol :span="12">
          <ElFormItem label="广告位名称" prop="name">
            <ElInput v-model="formData.name" placeholder="请输入广告位名称" />
          </ElFormItem>
        </ElCol>
        <ElCol :span="12">
          <ElFormItem label="别名" prop="nameAlise">
            <ElInput v-model="formData.nameAlise" placeholder="请输入别名" />
          </ElFormItem>
        </ElCol>
      </ElRow>
      <ElRow :gutter="20">
        <ElCol :span="12">
          <ElFormItem label="结算方式" prop="sspPayType">
            <ElSelect v-model="formData.sspPayType" placeholder="请选择结算方式">
              <ElOption label="分成" :value="1" />
              <ElOption label="RTB" :value="2" />
            </ElSelect>
          </ElFormItem>
        </ElCol>
        <ElCol :span="12">
          <ElFormItem label="分成比例" prop="sspDealRatio">
            <ElInputNumber
              v-model="formData.sspDealRatio"
              :min="0"
              :max="1"
              :step="0.1"
              :precision="2"
              style="width: 100%"
            />
          </ElFormItem>
        </ElCol>
      </ElRow>
      <ElRow :gutter="20">
        <ElCol :span="12">
          <ElFormItem label="宽度" prop="width">
            <ElInputNumber v-model="formData.width" :min="0" style="width: 100%" />
          </ElFormItem>
        </ElCol>
        <ElCol :span="12">
          <ElFormItem label="高度" prop="height">
            <ElInputNumber v-model="formData.height" :min="0" style="width: 100%" />
          </ElFormItem>
        </ElCol>
      </ElRow>
      <ElFormItem label="交互类型" prop="interactionType">
        <ElCheckboxGroup v-model="interactionTypes">
          <ElCheckbox :value="1">打开网页</ElCheckbox>
          <ElCheckbox :value="2">Deeplink</ElCheckbox>
          <ElCheckbox :value="4">直接下载</ElCheckbox>
          <ElCheckbox :value="8">广点通</ElCheckbox>
          <ElCheckbox :value="16">小程序</ElCheckbox>
          <ElCheckbox :value="32">应用商店</ElCheckbox>
          <ElCheckbox :value="64">快应用</ElCheckbox>
        </ElCheckboxGroup>
      </ElFormItem>
      <ElFormItem label="备注" prop="remark">
        <ElInput v-model="formData.remark" type="textarea" placeholder="请输入备注" />
      </ElFormItem>
    </ElForm>
    <template #footer>
      <div class="dialog-footer">
        <ElButton @click="dialogVisible = false">取消</ElButton>
        <ElButton type="primary" :loading="submitting" @click="handleSubmit">提交</ElButton>
      </div>
    </template>
  </ElDialog>
</template>

<script setup lang="ts">
  import { fetchCreateSlot, fetchUpdateSlot } from '@/api/media-manage'
  import type { FormInstance, FormRules } from 'element-plus'

  interface Props {
    visible: boolean
    type: string
    slotData?: Partial<Api.MediaManage.SlotListItem>
  }

  interface Emits {
    (e: 'update:visible', value: boolean): void
    (e: 'submit'): void
  }

  const props = defineProps<Props>()
  const emit = defineEmits<Emits>()

  const dialogVisible = computed({
    get: () => props.visible,
    set: (value) => emit('update:visible', value)
  })

  const dialogType = computed(() => props.type)
  const formRef = ref<FormInstance>()
  const submitting = ref(false)

  const formData = reactive({
    id: undefined as number | undefined,
    name: '',
    nameAlise: '',
    sspPayType: 1,
    sspDealRatio: 0.5,
    width: 0,
    height: 0,
    interactionType: 0,
    remark: ''
  })

  const interactionTypes = ref<number[]>([])

  watch(interactionTypes, (vals) => {
    formData.interactionType = vals.reduce((sum, val) => sum + val, 0)
  })

  const rules: FormRules = {
    name: [
      { required: true, message: '请输入广告位名称', trigger: 'blur' },
      { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
    ]
  }

  const initFormData = () => {
    const isEdit = props.type === 'edit' && props.slotData
    const row = props.slotData

    Object.assign(formData, {
      id: isEdit && row ? row.id : undefined,
      name: isEdit && row ? row.name || '' : '',
      nameAlise: isEdit && row ? row.nameAlise || '' : '',
      sspPayType: isEdit && row ? row.sspPayType || 1 : 1,
      sspDealRatio: isEdit && row ? row.sspDealRatio || 0.5 : 0.5,
      width: isEdit && row ? row.width || 0 : 0,
      height: isEdit && row ? row.height || 0 : 0,
      interactionType: isEdit && row ? row.interactionType || 0 : 0,
      remark: isEdit && row ? row.remark || '' : ''
    })

    const types: number[] = []
    let interaction = formData.interactionType
    if (interaction & 1) types.push(1)
    if (interaction & 2) types.push(2)
    if (interaction & 4) types.push(4)
    if (interaction & 8) types.push(8)
    if (interaction & 16) types.push(16)
    if (interaction & 32) types.push(32)
    if (interaction & 64) types.push(64)
    interactionTypes.value = types
  }

  watch(
    () => [props.visible, props.type, props.slotData],
    ([visible]) => {
      if (visible) {
        initFormData()
        nextTick(() => {
          formRef.value?.clearValidate()
        })
      }
    },
    { immediate: true }
  )

  const handleSubmit = async () => {
    if (!formRef.value) return

    await formRef.value.validate(async (valid) => {
      if (valid) {
        submitting.value = true
        try {
          if (dialogType.value === 'add') {
            await fetchCreateSlot({
              mediaId: 1,
              appId: 1,
              name: formData.name,
              nameAlise: formData.nameAlise,
              sspPayType: formData.sspPayType,
              sspDealRatio: formData.sspDealRatio,
              width: formData.width,
              height: formData.height,
              interactionType: formData.interactionType,
              remark: formData.remark
            })
            ElMessage.success('添加成功')
          } else {
            await fetchUpdateSlot({
              id: formData.id!,
              name: formData.name,
              nameAlise: formData.nameAlise,
              sspPayType: formData.sspPayType,
              sspDealRatio: formData.sspDealRatio,
              width: formData.width,
              height: formData.height,
              interactionType: formData.interactionType,
              remark: formData.remark
            })
            ElMessage.success('更新成功')
          }
          dialogVisible.value = false
          emit('submit')
        } finally {
          submitting.value = false
        }
      }
    })
  }
</script>
