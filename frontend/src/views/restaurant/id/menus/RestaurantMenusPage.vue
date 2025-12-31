<script setup>
import { ref, computed } from 'vue';
import { RouterLink, useRoute } from 'vue-router'; // Import useRoute to get dynamic params
import { ArrowLeft } from 'lucide-vue-next';
import { getMenuCategoriesByRestaurant } from '@/data/restaurantMenus';
import Card from '@/components/ui/Card.vue';

const route = useRoute();
const restaurantId = route.params.id || '1'; // Default to '1' if id is not available

const menuCategories = ref(getMenuCategoriesByRestaurant(restaurantId));

// "35,000원" -> 35000
const parsePrice = (price) => Number(String(price).replace(/[^\d]/g, ''));

// flat으로 펴서 지금 UI 구조 유지
const menuItems = computed(() =>
    menuCategories.value.flatMap((cat) =>
        cat.items.map((item) => ({
          id: item.id,
          name: item.name,
          description: item.description,
          category: cat.name,
          price: parsePrice(item.price),
        })),
    ),
);

const categories = computed(() => menuCategories.value.map((c) => c.name));

const menuItemCount = computed(() => menuItems.value.length);
</script>

<template>
  <div class="min-h-screen bg-[#f8f9fa]">
    <!-- Header -->
    <header class="sticky top-0 z-50 bg-white border-b border-[#e9ecef]">
      <div class="max-w-[500px] mx-auto px-4 h-14 flex items-center">
        <RouterLink
          :to="`/restaurant/${restaurantId}`"
          class="mr-3"
        >
          <ArrowLeft class="w-6 h-6 text-[#1e3a5f]" />
        </RouterLink>
        <h1 class="font-semibold text-[#1e3a5f] text-base">메뉴 전체보기</h1>
      </div>
    </header>

    <main class="max-w-[500px] mx-auto pb-8">
      <div class="bg-white px-4 py-3 border-b border-[#e9ecef]">
        <p class="text-sm text-[#495057] leading-relaxed">
          총 {{ menuItemCount.toLocaleString() }}개의 메뉴가 있어요.
        </p>
      </div>

      <div v-for="category in categories" :key="category" class="px-4 py-5">
        <h3 class="text-base font-semibold text-[#1e3a5f] mb-4">{{ category }}</h3>
        <div class="space-y-3">
          <Card
              v-for="item in menuItems.filter((mItem) => mItem.category === category)"
              :key="item.id"
              class="p-4 border-[#e9ecef] rounded-xl bg-white shadow-card hover:shadow-md transition-shadow"
          >
            <div class="flex items-start justify-between gap-3">
              <div class="flex-1">
                <h4 class="font-semibold text-[#1e3a5f] mb-1">{{ item.name }}</h4>
                <p v-if="item.description" class="text-xs text-[#6c757d] mb-2 leading-relaxed">
                  {{ item.description }}
                </p>
                <p class="text-base font-semibold text-[#1e3a5f]">{{ item.price.toLocaleString() }}원</p>
              </div>
            </div>
          </Card>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
</style>
