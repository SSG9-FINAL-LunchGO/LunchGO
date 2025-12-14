<script setup>
import { ref } from 'vue';
import { RouterLink } from 'vue-router';
import { ArrowLeft, User } from 'lucide-vue-next';
import Button from '@/components/ui/Button.vue';

const showSuccess = ref(false);
const specialInterests = ref(['']); // Initialize with one empty string for the first input

// Form fields state
const name = ref('테스터');
const nickname = ref('테스터닉네임');
const birthDate = ref('20xx.xx.xx');
const gender = ref('남'); // Default value for select
const phoneNumber = ref('010-xxxx-xxxx');
const companyName = ref('xx회사');
const hideCompanyName = ref(false); // Checkbox for company name
const workplaceAddress = ref('직장 주소');
const legalCardNumber = ref('');

const handleSave = () => {
  showSuccess.value = true;
  setTimeout(() => (showSuccess.value = false), 3000);
};

const addInterestField = () => {
  specialInterests.value.push('');
};

const updateInterest = (event, index) => {
  specialInterests.value[index] = event.target.value;
};
</script>

<template>
  <div class="min-h-screen bg-[#f8f9fa] pb-20">
    <header class="bg-white border-b border-[#dee2e6] sticky top-0 z-10">
      <div class="flex items-center justify-between px-4 h-14">
        <RouterLink to="/" class="p-2">
          <ArrowLeft class="w-6 h-6 text-[#1e3a5f]" />
        </RouterLink>
        <h1 class="text-lg font-bold text-[#1e3a5f]">마이페이지</h1>
        <div class="w-10" />
      </div>
    </header>

    <main class="px-4 py-6 space-y-6">
      <!-- Profile Photo -->
      <div class="bg-white rounded-xl p-4 shadow-sm">
        <div class="flex items-center gap-4">
          <div
            class="w-20 h-20 border-2 border-[#dee2e6] rounded-xl flex items-center justify-center bg-[#f8f9fa]"
          >
            <User class="w-10 h-10 text-[#adb5bd]" />
          </div>
          <Button
            variant="outline"
            class="h-9 px-4 border-[#dee2e6] text-[#495057] hover:bg-[#f8f9fa] rounded-lg bg-transparent text-sm"
          >
            프로필 사진 변경
          </Button>
        </div>
      </div>

      <!-- Email -->
      <div class="bg-white rounded-xl p-4 shadow-sm space-y-3">
        <label class="text-sm font-medium text-[#1e3a5f]">이메일</label>
        <input
          type="email"
          value="popsicle0404@test.com"
          readonly
          class="w-full h-10 px-3 border border-[#dee2e6] rounded-lg bg-[#f8f9fa] text-sm"
        />
        <Button
          variant="outline"
          class="w-full h-10 border-[#dee2e6] text-[#495057] hover:bg-[#f8f9fa] rounded-lg bg-transparent text-sm"
        >
          이메일 인증
        </Button>
      </div>

      <!-- Name and Nickname -->
      <div class="bg-white rounded-xl p-4 shadow-sm space-y-4">
        <div class="space-y-2">
          <label class="text-sm font-medium text-[#1e3a5f]">이름</label>
          <input
            type="text"
            v-model="name"
            class="w-full h-10 px-3 border border-[#dee2e6] rounded-lg text-sm"
          />
        </div>
        <div class="space-y-2">
          <label class="text-sm font-medium text-[#1e3a5f]">닉네임</label>
          <input
            type="text"
            v-model="nickname"
            class="w-full h-10 px-3 border border-[#dee2e6] rounded-lg text-sm"
          />
        </div>
      </div>

      <!-- Birth Date and Gender -->
      <div class="bg-white rounded-xl p-4 shadow-sm space-y-4">
        <div class="space-y-2">
          <label class="text-sm font-medium text-[#1e3a5f]">생년월일</label>
          <input
            type="text"
            v-model="birthDate"
            placeholder="YYYY.MM.DD"
            class="w-full h-10 px-3 border border-[#dee2e6] rounded-lg text-sm"
          />
        </div>
        <div class="space-y-2">
          <label class="text-sm font-medium text-[#1e3a5f]">성별</label>
          <select v-model="gender" class="w-full h-10 px-3 border border-[#dee2e6] rounded-lg text-sm">
            <option>남</option>
            <option>여</option>
          </select>
        </div>
      </div>

      <!-- Phone Number -->
      <div class="bg-white rounded-xl p-4 shadow-sm space-y-3">
        <label class="text-sm font-medium text-[#1e3a5f]">전화번호</label>
        <input
          type="tel"
          v-model="phoneNumber"
          class="w-full h-10 px-3 border border-[#dee2e6] rounded-lg text-sm"
        />
        <Button
          variant="outline"
          class="w-full h-10 border-[#dee2e6] text-[#495057] hover:bg-[#f8f9fa] rounded-lg bg-transparent text-sm"
        >
          인증번호 발송
        </Button>
        <input
          type="text"
          placeholder="인증번호를 입력하세요."
          class="w-full h-10 px-3 border border-[#dee2e6] rounded-lg text-sm"
        />
      </div>

      <!-- Company -->
      <div class="bg-white rounded-xl p-4 shadow-sm space-y-3">
        <label class="text-sm font-medium text-[#1e3a5f]">회사명</label>
        <input
          type="text"
          v-model="companyName"
          class="w-full h-10 px-3 border border-[#dee2e6] rounded-lg text-sm"
        />
        <label class="flex items-center gap-2 text-sm text-[#495057]">
          <input type="checkbox" v-model="hideCompanyName" class="w-4 h-4 border-[#dee2e6] rounded" />
          회사명 숨기기
        </label>
      </div>

      <!-- Workplace Address -->
      <div class="bg-white rounded-xl p-4 shadow-sm space-y-3">
        <label class="text-sm font-medium text-[#1e3a5f]">직장주소</label>
        <input
          type="text"
          v-model="workplaceAddress"
          class="w-full h-10 px-3 border border-[#dee2e6] rounded-lg text-sm"
        />
        <Button
          variant="outline"
          class="w-full h-10 border-[#dee2e6] text-[#495057] hover:bg-[#f8f9fa] rounded-lg bg-transparent text-sm"
        >
          주소 변경
        </Button>
      </div>

      <!-- Special Interests -->
      <div class="bg-white rounded-xl p-4 shadow-sm space-y-3">
        <label class="text-sm font-medium text-[#1e3a5f]">특이사항</label>
        <div class="space-y-2">
          <input
            v-for="(interest, index) in specialInterests"
            :key="index"
            type="text"
            placeholder="특이사항을 선택해주세요."
            :value="interest"
            @input="updateInterest($event, index)"
            class="w-full h-10 px-3 border border-[#dee2e6] rounded-lg text-sm"
          />
          <button
            @click="addInterestField"
            class="w-10 h-10 border border-[#dee2e6] rounded-lg flex items-center justify-center text-[#495057] hover:bg-[#f8f9fa]"
          >
            +
          </button>
        </div>
      </div>

      <!-- Legal Card Registration -->
      <div class="bg-white rounded-xl p-4 shadow-sm space-y-3">
        <label class="text-sm font-medium text-[#1e3a5f]">법인카드 등록</label>
        <input
          type="text"
          placeholder="xxxx-xxxx-xxxx-xxxx"
          v-model="legalCardNumber"
          class="w-full h-10 px-3 border border-[#dee2e6] rounded-lg text-sm"
        />
        <Button
          variant="outline"
          class="w-full h-10 border-[#dee2e6] text-[#495057] hover:bg-[#f8f9fa] rounded-lg bg-transparent text-sm"
        >
          등록하기
        </Button>
      </div>

      <!-- Save Button -->
      <Button
        @click="handleSave"
        class="w-full h-12 bg-gradient-to-r from-[#ff6b4a] to-[#ffc4b8] text-white font-medium rounded-xl hover:opacity-90"
      >
        수정하기
      </Button>

      <!-- Success Message -->
      <div v-if="showSuccess" class="fixed bottom-24 left-1/2 -translate-x-1/2 bg-[#e7f5ff] border border-[#339af0] rounded-lg px-6 py-3 flex items-center gap-2 shadow-lg">
        <svg class="w-5 h-5 text-[#339af0]" fill="currentColor" viewBox="0 0 20 20">
          <path
            fill-rule="evenodd"
            d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
            clip-rule="evenodd"
          />
        </svg>
        <span class="text-sm font-medium text-[#1e3a5f]">수정이 완료되었습니다</span>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
</style>
