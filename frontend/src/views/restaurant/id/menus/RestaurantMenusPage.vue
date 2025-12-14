<script setup>
import { ArrowLeft } from 'lucide-vue-next'; // Import Lucide icons for Vue
import { RouterLink, useRoute } from 'vue-router'; // Import Vue RouterLink and useRoute
import Card from '@/components/ui/Card.vue';

const route = useRoute();
const restaurantId = route.params.id || '1'; // Access the dynamic ID, default for now

const menuCategories = [
  {
    name: '코스 메뉴',
    items: [
      { name: 'A코스', price: '35,000원', description: '전채 + 메인 + 디저트' },
      { name: 'B코스', price: '45,000원', description: '전채 + 메인 + 디저트 + 와인' },
      { name: 'C코스', price: '55,000원', description: '전채 + 메인 + 디저트 + 프리미엄 와인' },
      { name: 'D코스', price: '65,000원', description: '최고급 전채 + 프리미엄 메인 + 특별 디저트 + 프리미엄 와인' },
    ],
  },
  {
    name: '단품 메뉴',
    items: [
      { name: '파스타', price: '18,000원', description: '크림/토마토 소스 선택' },
      { name: '스테이크', price: '28,000원', description: '250g, 미디움 추천' },
      { name: '리조또', price: '16,000원', description: '해산물 리조또' },
      { name: '피자', price: '22,000원', description: '마르게리타/페퍼로니' },
      { name: '샐러드', price: '12,000원', description: '시저 샐러드' },
      { name: '수프', price: '10,000원', description: '버섯 크림 수프' },
    ],
  },
  {
    name: '사이드 메뉴',
    items: [
      { name: '감자튀김', price: '8,000원' },
      { name: '마늘빵', price: '6,000원' },
      { name: '치즈스틱', price: '9,000원' },
      { name: '새우튀김', price: '12,000원' },
    ],
  },
  {
    name: '음료',
    items: [
      { name: '와인(레드)', price: '45,000원', description: '보틀' },
      { name: '와인(화이트)', price: '42,000원', description: '보틀' },
      { name: '맥주', price: '6,000원' },
      { name: '소프트드링크', price: '3,000원' },
      { name: '에스프레소', price: '4,000원' },
      { name: '아메리카노', price: '4,500원' },
    ],
  },
  {
    name: '디저트',
    items: [
      { name: '티라미수', price: '8,000원' },
      { name: '판나코타', price: '7,000원' },
      { name: '아이스크림', price: '5,000원' },
      { name: '치즈케이크', price: '9,000원' },
    ],
  },
];
</script>

<template>
  <div class="min-h-screen bg-[#f8f9fa]">
    <!-- Header -->
    <header class="sticky top-0 z-50 bg-white border-b border-[#e9ecef]">
      <div class="max-w-[500px] mx-auto px-4 h-14 flex items-center">
        <RouterLink :to="`/restaurant/${restaurantId}`" class="mr-3">
          <ArrowLeft class="w-6 h-6 text-[#1e3a5f]" />
        </RouterLink>
        <h1 class="font-semibold text-[#1e3a5f] text-base">전체 메뉴</h1>
      </div>
    </header>

    <main class="max-w-[500px] mx-auto px-4 py-5 pb-8">
      <div class="space-y-6">
        <div v-for="(category, idx) in menuCategories" :key="idx">
          <h3 class="text-base font-semibold text-[#1e3a5f] mb-3 px-1">{{ category.name }}</h3>
          <div class="space-y-2">
            <Card
              v-for="(item, itemIdx) in category.items"
              :key="itemIdx"
              class="p-4 border-[#e9ecef] rounded-xl bg-white shadow-card hover:shadow-md transition-shadow"
            >
              <div class="flex items-center justify-between">
                <div class="flex-1">
                  <p class="font-semibold text-[#1e3a5f] mb-1">{{ item.name }}</p>
                  <p v-if="item.description" class="text-xs text-[#6c757d] leading-relaxed">
                    {{ item.description }}
                  </p>
                </div>
                <p class="font-semibold text-[#1e3a5f] ml-3">{{ item.price }}</p>
              </div>
            </Card>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
</style>
