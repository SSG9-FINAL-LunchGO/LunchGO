<script setup lang="ts">
import { ref, watch } from 'vue'

const props = defineProps<{
  isVisible: boolean;
  userType: string;
}>()

const emit = defineEmits(['close'])

const name = ref('')
const email = ref('')
const phone = ref('')

// 인증번호 관련 상태
const isCodeSent = ref(false)
const verifyCode = ref('')

// props.isVisible을 감시하다가 false(닫힘)가 되면 실행
watch(() => props.isVisible, (newVal) => {
  if (!newVal) {
    name.value = ''
    phone.value = ''
    verifyCode.value = ''
    isCodeSent.value = false 
  }
})

// 이메일 찾기 (최종 제출)
const handleFindPwd = () => {
  if (!isCodeSent.value) {
    alert('인증번호를 먼저 발송해주세요.');
    return;
  }
  
  alert(`${props.userType} 유형의 아이디 찾기를 시도합니다.\n이름: ${name.value}\n전화번호: ${phone.value}\n인증번호: ${verifyCode.value}`)
  // 성공 후 로직 (모달 닫기 등)
  // emit('close')
}

// 인증번호 발송/재전송 처리
const handleSendVerifyCode = () => {
  if (!phone.value) {
    alert('휴대폰 번호를 입력해주세요.');
    return;
  }
  
  alert(`인증번호를 발송했습니다: ${phone.value}`);
  isCodeSent.value = true; // 이 값이 true가 되면 입력창이 나타나고 버튼 텍스트가 바뀜
}
</script>

<template>
  <Teleport to="body">
    <div v-if="isVisible" class="modal-overlay" @click.self="$emit('close')">
      <div class="modal-content">
        
        <div class="modal-header">
          <h3>비밀번호 찾기</h3>
          <button class="close-btn" @click="$emit('close')">&times;</button>
        </div>

        <div class="modal-body">
          <p class="guide-text">가입 시 등록한 정보를 입력해주세요.</p>
          
          <form @submit.prevent="handleFindPwd">
            <div class="input-group">
              <label for="find-email">이메일</label>
              <input 
                id="find-email"
                v-model="email" 
                type="email" 
                placeholder="example@email.com" 
                required 
              />
            </div>


            <div class="input-group">
              <label for="find-name">이름</label>
              <input 
                id="find-name"
                v-model="name" 
                type="text" 
                placeholder="이름을 입력하세요" 
                required 
              />
            </div>

            <div class="input-group">
              <label for="find-phone">휴대폰 번호</label>
              <div class="input-with-button">
                <input 
                  id="find-phone"
                  v-model="phone" 
                  type="tel" 
                  placeholder="010-0000-0000" 
                  required 
                  class="flex-grow-input"
                  :disabled="isCodeSent" 
                />
                <button type="button" class="btn-secondary" @click="handleSendVerifyCode">
                   {{ isCodeSent ? '재전송' : '인증번호 발송' }}
                </button>
              </div>
            </div>

            <div v-if="isCodeSent" class="input-group slide-in">
              <input 
                v-model="verifyCode" 
                type="text" 
                placeholder="인증번호 6자리를 입력하세요" 
                class="input-field"
                maxlength="6"
              />
              <p class="timer-text">02:59</p>
            </div>

            <button type="submit" class="btn-confirm">비밀번호 변경하기</button>

            <div v-if="isCodeSent" class="resend-link-container">
              <button type="button" class="btn-text-link" @click="handleSendVerifyCode">
                인증번호가 오지 않나요? <span>재전송</span>
              </button>
            </div>

          </form>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
/* 기존 스타일 유지 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
  backdrop-filter: blur(2px);
}

.modal-content {
  background: white;
  width: 90%;
  max-width: 400px;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  animation: slideUp 0.3s ease-out;
}

@keyframes slideUp {
  from { transform: translateY(20px); opacity: 0; }
  to { transform: translateY(0); opacity: 1; }
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.modal-header h3 {
  margin: 0;
  font-size: 18px;
  color: #1E3A5F;
  font-weight: 700;
}

.close-btn {
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #ADB5BD;
  padding: 0;
  line-height: 1;
}

.guide-text {
  font-size: 14px;
  color: #6C757D;
  margin-bottom: 24px;
  line-height: 1.5;
}

.input-group {
  margin-bottom: 16px;
  position: relative;
}

.input-group label {
  display: block;
  font-size: 12px;
  font-weight: 600;
  color: #1E3A5F;
  margin-bottom: 6px;
}

.input-group input {
  width: 100%;
  height: 48px;
  padding: 0 16px;
  border: 1.5px solid #E9ECEF;
  border-radius: 8px;
  box-sizing: border-box;
  font-size: 14px;
  color: #1E3A5F;
  transition: border-color 0.2s;
}

.input-group input:focus {
  outline: none;
  border-color: #FF6B4A;
}

.input-group input:disabled {
  background-color: #F8F9FA;
  color: #ADB5BD;
}

.input-with-button {
  display: flex;
  gap: 8px;
  width: 100%;
}

.input-with-button input.flex-grow-input {
  flex: 1;
}

.btn-secondary {
  height: 48px;
  padding: 0 16px;
  background-color: #F8F9FA;
  border: 1.5px solid #E9ECEF;
  border-radius: 8px;
  color: #495057;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  white-space: nowrap;
  transition: all 0.2s;
}

.btn-secondary:hover {
  background-color: #E9ECEF;
  border-color: #DEE2E6;
  color: #1E3A5F;
}

.slide-in {
    animation: fadeIn 0.3s ease-out;
}
@keyframes fadeIn {
    from { opacity: 0; transform: translateY(-5px); }
    to { opacity: 1; transform: translateY(0); }
}

.timer-text {
    position: absolute;
    right: 16px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 13px;
    color: #FF6B4A;
    margin: 0;
    pointer-events: none;
}

.btn-confirm {
  width: 100%;
  height: 48px;
  background-color: #FF6B4A;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: 700;
  font-size: 16px;
  cursor: pointer;
  margin-top: 8px;
  transition: background-color 0.2s;
}

.btn-confirm:hover {
  background-color: #E5553A;
}

.resend-link-container {
  margin-top: 12px;
  text-align: center;
  animation: fadeIn 0.5s ease-out;
}

.btn-text-link {
  background: none;
  border: none;
  color: #ADB5BD; /* 기본 문구 색상 (연한 회색) */
  font-size: 13px;
  cursor: pointer;
  padding: 0;
  font-family: inherit; /* 폰트 상속 */
}

.btn-text-link span {
  color: #6C757D; /* '재전송' 강조 색상 (진한 회색) */
  text-decoration: underline; /* 밑줄 */
  margin-left: 4px;
  font-weight: 600;
  transition: color 0.2s;
}

.btn-text-link:hover span {
  color: #1E3A5F; /* 호버 시 색상 변경 */
}
</style>