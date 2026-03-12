import request from '@/utils/http'

export function fetchMediaRegister(params: Api.MediaAuth.RegisterParams) {
  return request.post<Api.MediaAuth.RegisterResponse>({
    url: '/api/media/auth/register',
    params
  })
}

export function fetchMediaLogin(params: Api.MediaAuth.LoginParams) {
  return request.post<Api.MediaAuth.LoginResponse>({
    url: '/api/media/auth/login',
    params
  })
}

export function fetchMediaValidateToken() {
  return request.get<{ id: number; account: string; name: string; enable: number }>({
    url: '/api/media/auth/validate'
  })
}

export function fetchMediaLogout() {
  return request.post({
    url: '/api/media/auth/logout'
  })
}
