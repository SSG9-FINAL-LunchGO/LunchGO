<script setup>
import { User, Bell, LogOut } from 'lucide-vue-next';
import { RouterLink, useRouter } from 'vue-router';

const router = useRouter();

const handleLogout = () => {
  const confirmLogout = confirm('정말 로그아웃 하시겠습니까?');

  if (confirmLogout) {
    try {
      // 여기에 실제 로그아웃 로직 작성 (예: Pinia action 호출, 토큰 삭제 등)
      // await authStore.logout();
      console.log('로그아웃 로직 실행됨');

      // 로그아웃 후 첫 페이지로 이동
      router.push('/');
    } catch (error) {
      console.error('로그아웃 실패:', error);
    }
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
        LunchGo 총관리자
      </h1>
    </RouterLink>
    <div class="flex items-center gap-4">
      <button class="text-[#1e3a5f] hover:text-[#FF6B4A] transition-colors">
        <Bell class="w-6 h-6" />
      </button>
      <div class="flex items-center gap-4">
        <span class="text-sm text-gray-600">관리자님 안녕하세요!</span>

        <div class="relative">
          <button
            @click="handleLogout"
            class="flex items-center gap-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
          >
            <span class="text-sm">로그아웃</span>
            <LogOut class="w-4 h-4" />
          </button>
        </div>
      </div>
    </div>
  </header>
</template>
