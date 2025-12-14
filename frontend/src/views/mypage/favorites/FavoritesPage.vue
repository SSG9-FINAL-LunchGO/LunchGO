<script setup>
import { ArrowLeft, Heart, Star } from 'lucide-vue-next'; // Import Lucide icons for Vue
import { RouterLink } from 'vue-router'; // Import Vue RouterLink
import Button from '@/components/ui/Button.vue'; // Import custom Button
import Card from '@/components/ui/Card.vue';

const favorites = [
  {
    id: 1,
    name: '판교 숯불갈비',
    category: '한식',
    rating: 4.8,
    reviews: 245,
    description: '회식 분위기 최고, 술 사실이 풍부해요',
    price: '18,000원 · 4인 ~ 12인',
    badgeColor: 'bg-emerald-500',
  },
  {
    id: 2,
    name: '중화요리 맛집',
    category: '중식',
    rating: 4.7,
    reviews: 189,
    description: '단체석 완비, 룸 예약 가능',
    price: '15,000원 · 4인 ~ 10인',
    badgeColor: 'bg-emerald-600',
  },
  {
    id: 3,
    name: '이탈리안 레스토랑',
    category: '양식',
    rating: 4.9,
    reviews: 312,
    description: '분위기 좋고, 와인 선택 다양',
    price: '22,000원 · 4인 ~ 8인',
    badgeColor: 'bg-emerald-500',
  },
];
</script>

<template>
  <div class="min-h-screen bg-[#f8f9fa] pb-20">
    <!-- Header -->
    <header class="sticky top-0 z-50 bg-white border-b border-[#e9ecef]">
      <div class="max-w-[500px] mx-auto px-4 h-14 flex items-center justify-between">
        <RouterLink to="/mypage">
          <Button variant="ghost" size="icon" class="w-9 h-9 hover:bg-[#f8f9fa]">
            <ArrowLeft class="w-5 h-5 text-[#1e3a5f]" />
          </Button>
        </RouterLink>
        <h1 class="font-semibold text-[#1e3a5f] text-base">나의 즐겨찾기</h1>
        <div class="w-9" />
      </div>
    </header>

    <main class="max-w-[500px] mx-auto">
      <!-- Count -->
      <div class="px-4 py-4 bg-white border-b border-[#e9ecef]">
        <p class="text-sm text-[#495057]">
          총 <span class="font-semibold text-[#ff6b4a]">{{ favorites.length }}개</span>의 즐겨찾기
        </p>
      </div>

      <!-- Favorites List -->
      <div class="px-4 py-4 space-y-3">
        <Card
          v-for="restaurant in favorites"
          :key="restaurant.id"
          class="overflow-hidden border-[#e9ecef] rounded-xl bg-white shadow-card"
        >
          <div class="flex gap-3 p-3">
            <!-- Restaurant Image -->
            <div class="relative w-24 h-24 flex-shrink-0 rounded-lg overflow-hidden bg-blue-500">
              <div class="absolute top-2 left-2 z-10">
                <span :class="`${restaurant.badgeColor} text-white text-xs font-medium px-2 py-0.5 rounded`">
                  {{ restaurant.category }}
                </span>
              </div>
              <div class="absolute inset-0 flex items-center justify-center text-white text-sm font-medium">
                image
              </div>
            </div>

            <!-- Restaurant Info -->
            <div class="flex-1 min-w-0">
              <div class="flex items-start justify-between mb-1">
                <h4 class="font-semibold text-[#1e3a5f] text-sm">{{ restaurant.name }}</h4>
                <Button variant="ghost" size="icon" class="w-8 h-8 -mt-1 -mr-1 hover:bg-transparent">
                  <Heart class="w-5 h-5 fill-[#ff6b4a] text-[#ff6b4a]" />
                </Button>
              </div>
              <div class="flex items-center gap-1 mb-2">
                <Star class="w-3.5 h-3.5 fill-[#ffc107] text-[#ffc107]" />
                <span class="text-sm font-medium text-[#1e3a5f]">{{ restaurant.rating }}</span>
                <span class="text-xs text-[#6c757d]">({{ restaurant.reviews }})</span>
              </div>
              <p class="text-xs text-[#6c757d] mb-2 line-clamp-1">{{ restaurant.description }}</p>
              <p class="text-sm font-semibold text-[#1e3a5f]">{{ restaurant.price }}</p>
            </div>
          </div>

          <!-- Actions -->
          <div class="flex gap-2 px-3 pb-3">
            <RouterLink :to="`/restaurant/${restaurant.id}`" class="flex-1">
              <Button
                variant="outline"
                class="w-full h-9 text-sm border-[#dee2e6] text-[#495057] bg-white hover:bg-[#f8f9fa] rounded-lg bg-transparent"
              >
                상세보기
              </Button>
            </RouterLink>
            <RouterLink :to="`/restaurant/${restaurant.id}?type=preorder`" class="flex-1">
              <Button class="w-full h-9 text-sm gradient-primary text-white hover:opacity-90 rounded-lg shadow-button-hover">
                예약하기
              </Button>
            </RouterLink>
          </div>
        </Card>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
</style>
