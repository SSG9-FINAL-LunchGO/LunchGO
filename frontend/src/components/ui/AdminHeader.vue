<script setup>
import { computed } from 'vue';
import { RouterLink, useRouter } from 'vue-router';
import { Bell, LogOut } from 'lucide-vue-next';
import httpRequest from '@/router/httpRequest';
import { useAccountStore } from '@/stores/account';

const router = useRouter();
const accountStore = useAccountStore();

const getStoredMember = () => {
  if (typeof window === 'undefined') return null;
  const raw = localStorage.getItem('member');
  if (!raw) return null;
  try {
    return JSON.parse(raw);
  } catch (error) {
    return null;
  }
};

const member = computed(() => accountStore.member || getStoredMember());
const userName = computed(()=> {return "관리자";});

const handleLogout = async () => {
  const confirmLogout = confirm('정말 로그아웃 하시겠습니까?');
  if (!confirmLogout) return;

  try {
    await httpRequest.post('/api/logout', {});
    
    alert('로그아웃 되었습니다.');
  } catch (error) {
    console.warn('로그아웃 요청 실패:', error);
  } finally {
    accountStore.clearAccount();
    router.push('/');
  }
};
</script>

<template>
  <header
    class="bg-white border-b border-[#e9ecef] px-8 py-5 flex items-center justify-between"
  >
    <RouterLink to="/admin/dashboard">
      <h1
        class="text-2xl font-bold text-[#1E3A5F] hover:text-[#FF6B4A] transition-colors cursor-pointer"
      >
        LunchGo 관리자
      </h1>
    </RouterLink>
    <div class="flex items-center gap-4">
      <button class="text-[#1e3a5f] hover:text-[#FF6B4A] transition-colors">
        <Bell class="w-6 h-6" />
      </button>
      <div class="flex items-center gap-4">
        <span class="text-sm text-gray-600">
          <span class="text-[#1e3a5f] font-bold">{{ userName }}</span>
          <span>님, 안녕하세요</span>
        </span>

        <button
          @click="handleLogout"
          class="flex items-center gap-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
        >
          <span class="text-sm">로그아웃</span>
          <LogOut class="w-4 h-4" />
        </button>
      </div>
    </div>
  </header>
</template>
