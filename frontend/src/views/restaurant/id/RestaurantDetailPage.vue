<script setup>
import { ref } from 'vue';
import { RouterLink, useRoute } from 'vue-router'; // Import useRoute to get dynamic params
import {
  ArrowLeft,
  MapPin,
  Star,
  Clock,
  Users,
  Phone,
  ChevronRight,
  ChevronLeft,
} from 'lucide-vue-next';
import Button from '@/components/ui/Button.vue';
import Card from '@/components/ui/Card.vue';

const route = useRoute();
const restaurantId = route.params.id || '1'; // Default to '1' if id is not available

const currentImageIndex = ref(0);

const restaurantImages = ref([
  { url: '/modern-korean-restaurant-interior.jpg', alt: '식당 메인 이미지' },
  { url: '/elegant-dining-room-setup.jpg', alt: '식당 내부 전경' },
  { url: '/korean-course-meal-plating.jpg', alt: '대표 메뉴 이미지' },
  { url: '/restaurant-private-room-atmosphere.jpg', alt: '식당 분위기' },
]);

const handlePrevImage = () => {
  currentImageIndex.value =
    currentImageIndex.value === 0
      ? restaurantImages.value.length - 1
      : currentImageIndex.value - 1;
};

const handleNextImage = () => {
  currentImageIndex.value =
    currentImageIndex.value === restaurantImages.value.length - 1
      ? 0
      : currentImageIndex.value + 1;
};

const representativeMenus = ref([
  {
    name: 'A코스',
    price: '35,000원',
    description: '전채 + 메인 + 디저트',
    image: '/korean-appetizer-main-dessert.jpg',
  },
  {
    name: 'B코스',
    price: '45,000원',
    description: '전채 + 메인 + 디저트 + 와인',
    image: '/premium-course-meal-with-wine.jpg',
  },
  { name: '파스타', price: '18,000원', description: '단품', image: '/italian-pasta-dish.png' },
]);

const representativeReviews = ref([
  {
    id: 1,
    author: '김**',
    company: '네이버',
    rating: 5,
    date: '2024.01.15',
    content: '회식하기 정말 좋았어요. 음식도 맛있고 분위기도 최고였습니다!',
    tags: ['룸이 있어 프라이빗해요', '대화하기 좋아요', '양이 푸짐해요'],
    images: ['/korean-appetizer-main-dessert.jpg', '/premium-course-meal-with-wine.jpg'],
  },
  {
    id: 2,
    author: '이**',
    company: null,
    rating: 5,
    date: '2024.01.10',
    content: '직원분들이 친절하시고 코스 구성이 알차서 만족스러웠습니다.',
    tags: ['사장님이 친절해요', '예약 시간 맞춰 세팅돼요'],
    images: [],
  },
  {
    id: 3,
    author: '박**',
    company: '카카오',
    rating: 4,
    date: '2024.01.05',
    content: '가격 대비 훌륭한 퀄리티입니다. 다음에 또 방문할게요.',
    tags: ['법카 쓰기 좋은 가격대에요', '주차가 편해요'],
    images: ['/italian-pasta-dish.png'],
  },
]);
</script>

<template>
  <div class="min-h-screen bg-[#f8f9fa]">
    <!-- Header -->
    <header class="sticky top-0 z-50 bg-white border-b border-[#e9ecef]">
      <div class="max-w-[500px] mx-auto px-4 h-14 flex items-center">
        <RouterLink to="/" class="mr-3">
          <ArrowLeft class="w-6 h-6 text-[#1e3a5f]" />
        </RouterLink>
        <h1 class="font-semibold text-[#1e3a5f] text-base">식당 정보</h1>
      </div>
    </header>

    <main class="max-w-[500px] mx-auto pb-24">
      <div class="relative w-full h-64 bg-gradient-to-br from-orange-400 to-pink-400 overflow-hidden">
        <img
          :src="restaurantImages[currentImageIndex].url || '/placeholder.svg'"
          :alt="restaurantImages[currentImageIndex].alt"
          class="w-full h-full object-cover"
        />

        <!-- Left Arrow -->
        <button
          @click="handlePrevImage"
          class="absolute left-3 top-1/2 -translate-y-1/2 w-10 h-10 rounded-full bg-white/80 hover:bg-white flex items-center justify-center shadow-lg transition-all"
          aria-label="이전 이미지"
        >
          <ChevronLeft class="w-5 h-5 text-[#1e3a5f]" />
        </button>

        <!-- Right Arrow -->
        <button
          @click="handleNextImage"
          class="absolute right-3 top-1/2 -translate-y-1/2 w-10 h-10 rounded-full bg-white/80 hover:bg-white flex items-center justify-center shadow-lg transition-all"
          aria-label="다음 이미지"
        >
          <ChevronRight class="w-5 h-5 text-[#1e3a5f]" />
        </button>

        <!-- Image Indicators -->
        <div class="absolute bottom-3 left-1/2 -translate-x-1/2 flex gap-2">
          <button
            v-for="(_, idx) in restaurantImages"
            :key="idx"
            @click="currentImageIndex = idx"
            :class="`w-2 h-2 rounded-full transition-all ${
              idx === currentImageIndex ? 'bg-white w-6' : 'bg-white/50 hover:bg-white/75'
            }`"
            :aria-label="`이미지 ${idx + 1}로 이동`"
          />
        </div>
      </div>

      <!-- Restaurant Info -->
      <div class="bg-white px-4 py-5 border-b border-[#e9ecef]">
        <div class="flex items-start justify-between mb-3">
          <div class="flex-1">
            <h2 class="text-xl font-bold text-[#1e3a5f] mb-2">식당명</h2>
            <div class="flex items-center gap-1 mb-2">
              <Star class="w-4 h-4 fill-[#ffc107] text-[#ffc107]" />
              <span class="text-base font-semibold text-[#1e3a5f]">4.8</span>
              <span class="text-sm text-[#6c757d]">(245개 리뷰)</span>
            </div>
            <p class="text-sm text-[#6c757d] mb-3 leading-relaxed">
              1순위 태그명, 2순위 태그명 (Ex. 회식 분위기 최고, 술 사실이 풍부해요)
            </p>
          </div>
        </div>

        <div class="space-y-2.5">
          <div class="flex items-start gap-2 text-sm">
            <MapPin class="w-4 h-4 text-[#6c757d] mt-0.5 flex-shrink-0" />
            <span class="text-[#495057] leading-relaxed">서울시 강남구 테헤란로 123</span>
          </div>
          <div class="flex items-start gap-2 text-sm">
            <Clock class="w-4 h-4 text-[#6c757d] mt-0.5 flex-shrink-0" />
            <span class="text-[#495057] leading-relaxed">영업시간: 11:00 - 22:00 (라스트오더 21:00)</span>
          </div>
          <div class="flex items-start gap-2 text-sm">
            <Phone class="w-4 h-4 text-[#6c757d] mt-0.5 flex-shrink-0" />
            <span class="text-[#495057] leading-relaxed">02-1234-5678</span>
          </div>
          <div class="flex items-start gap-2 text-sm">
            <Users class="w-4 h-4 text-[#6c757d] mt-0.5 flex-shrink-0" />
            <span class="text-[#495057] leading-relaxed">최소 4인 ~ 최대 12인</span>
          </div>
        </div>
      </div>

      <!-- Representative Menus -->
      <div class="px-4 py-5 bg-white border-b border-[#e9ecef]">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-[#1e3a5f]">대표 메뉴</h3>
          <RouterLink
            :to="`/restaurant/${restaurantId}/menus`"
            class="flex items-center gap-1 text-sm text-[#ff6b4a] font-medium hover:text-[#ff8570] transition-colors"
          >
            메뉴 전체보기
            <ChevronRight class="w-4 h-4" />
          </RouterLink>
        </div>

        <div class="space-y-3">
          <Card
            v-for="(item, idx) in representativeMenus"
            :key="idx"
            class="p-3 border-[#e9ecef] rounded-xl bg-white shadow-card hover:shadow-md transition-shadow"
          >
            <div class="flex items-center gap-3">
              <img
                :src="item.image || '/placeholder.svg'"
                :alt="item.name"
                class="w-20 h-20 rounded-lg object-cover flex-shrink-0"
              />
              <div class="flex-1 min-w-0">
                <p class="font-semibold text-[#1e3a5f] mb-1">{{ item.name }}</p>
                <p v-if="item.description" class="text-xs text-[#6c757d] leading-relaxed mb-2">
                  {{ item.description }}
                </p>
                <p class="font-semibold text-[#ff6b4a]">{{ item.price }}</p>
              </div>
            </div>
          </Card>
        </div>
      </div>

      <!-- Representative Reviews -->
      <div class="px-4 py-5 bg-white border-b border-[#e9ecef]">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-[#1e3a5f]">리뷰</h3>
          <RouterLink
            :to="`/restaurant/${restaurantId}/reviews`"
            class="flex items-center gap-1 text-sm text-[#ff6b4a] font-medium hover:text-[#ff8570] transition-colors"
          >
            리뷰 전체보기
            <ChevronRight class="w-4 h-4" />
          </RouterLink>
        </div>

        <div class="space-y-3">
          <RouterLink
            v-for="review in representativeReviews"
            :key="review.id"
            :to="`/restaurant/${restaurantId}/reviews/${review.id}`"
          >
            <Card class="p-4 border-[#e9ecef] rounded-xl bg-white shadow-card hover:shadow-md transition-shadow cursor-pointer">
              <div class="flex items-center justify-between mb-2">
                <div class="flex items-center gap-2">
                  <span class="font-semibold text-[#1e3a5f] text-sm">{{ review.author }}</span>
                  <span v-if="review.company" class="font-semibold text-[#1e3a5f] text-sm"
                    >({{ review.company }})</span
                  >
                  <div class="flex items-center gap-1">
                    <Star
                      v-for="(_, i) in Array.from({ length: review.rating })"
                      :key="i"
                      class="w-3.5 h-3.5 fill-[#ffc107] text-[#ffc107]"
                    />
                  </div>
                </div>
                <span class="text-xs text-[#6c757d]">{{ review.date }}</span>
              </div>
              <p class="text-sm text-[#495057] leading-relaxed mb-2">{{ review.content }}</p>
              <div v-if="review.tags && review.tags.length > 0" class="flex flex-wrap gap-1.5">
                <span
                  v-for="(tag, idx) in review.tags"
                  :key="idx"
                  class="inline-block px-2.5 py-1 text-xs rounded-full bg-gradient-to-r from-[#ff6b4a] to-[#ffc4b8] text-white"
                >
                  {{ tag }}
                </span>
              </div>
            </Card>
          </RouterLink>
        </div>
      </div>

      <!-- Additional Info -->
      <div class="bg-white px-4 py-5 border-t border-[#e9ecef]">
        <h3 class="text-base font-semibold text-[#1e3a5f] mb-3">예약 안내</h3>
        <div class="space-y-2 text-sm text-[#495057] leading-relaxed">
          <p>• 예약은 최소 1일 전까지 가능합니다.</p>
          <p>• 예약 취소는 1일 전까지 무료이며, 당일 취소 시 위약금이 발생할 수 있습니다.</p>
          <p>• 인원 변경은 예약일 기준 2일 전까지 가능합니다.</p>
          <p>• 노쇼 시 향후 예약이 제한될 수 있습니다.</p>
        </div>
      </div>
    </main>

    <!-- Fixed Bottom Buttons -->
    <div class="fixed bottom-0 left-0 right-0 bg-white border-t border-[#e9ecef] z-50 shadow-lg">
      <div class="max-w-[500px] mx-auto px-4 py-3">
        <div class="flex gap-3">
          <RouterLink :to="`/restaurant/${restaurantId}/booking?type=preorder`" class="flex-1">
            <Button
              class="w-full h-12 bg-white border-2 border-[#ff6b4a] text-[#ff6b4a] font-semibold text-base rounded-xl hover:bg-[#fff5f3] transition-colors"
            >
              선주문/선결제
            </Button>
          </RouterLink>
          <RouterLink :to="`/restaurant/${restaurantId}/booking?type=reservation`" class="flex-1">
            <Button class="w-full h-12 gradient-primary text-white font-semibold text-base rounded-xl shadow-button-hover hover:shadow-button-pressed">
              예약하기
            </Button>
          </RouterLink>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
</style>
