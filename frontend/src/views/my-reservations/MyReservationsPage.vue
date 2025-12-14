<script setup>
import { Calendar, Clock, Users, MapPin, ArrowLeft } from 'lucide-vue-next'; // Import Lucide icons for Vue
import { RouterLink } from 'vue-router'; // Import Vue RouterLink
import Button from '@/components/ui/Button.vue'; // Import custom Button
import Card from '@/components/ui/Card.vue';

const reservations = [
  {
    id: 1,
    confirmationNumber: 'LG2024121500123',
    restaurant: {
      name: '식당명',
      address: '서울시 강남구 테헤란로 123',
    },
    booking: {
      date: '2024년 12월 15일 (금)',
      time: '18:00',
      partySize: 4,
    },
    status: 'confirmed',
  },
];
</script>

<template>
  <div class="min-h-screen bg-[#f8f9fa]">
    <header class="sticky top-0 z-50 bg-white border-b border-[#e9ecef]">
      <div class="max-w-[500px] mx-auto px-4 h-14 flex items-center">
        <RouterLink to="/" class="mr-3">
          <ArrowLeft class="w-6 h-6 text-[#1e3a5f]" />
        </RouterLink>
        <h1 class="font-semibold text-[#1e3a5f] text-base">예약 내역</h1>
      </div>
    </header>

    <main class="max-w-[500px] mx-auto px-4 py-5">
      <div class="space-y-3">
        <Card
          v-for="reservation in reservations"
          :key="reservation.id"
          class="overflow-hidden border-[#e9ecef] rounded-xl bg-white shadow-card hover:shadow-lg transition-shadow"
        >
          <div class="p-4">
            <div class="flex items-start justify-between mb-3">
              <div>
                <h3 class="font-semibold text-[#1e3a5f] text-base mb-1">
                  {{ reservation.restaurant.name }}
                </h3>
                <p class="text-xs text-[#6c757d]">예약번호: {{ reservation.confirmationNumber }}</p>
              </div>
              <span class="px-2.5 py-1 rounded-full bg-emerald-50 text-emerald-600 text-xs font-medium">
                예약 확정
              </span>
            </div>

            <div class="space-y-2 mb-4">
              <div class="flex items-center gap-2 text-sm">
                <Calendar class="w-4 h-4 text-[#6c757d]" />
                <span class="text-[#495057]">{{ reservation.booking.date }}</span>
              </div>
              <div class="flex items-center gap-2 text-sm">
                <Clock class="w-4 h-4 text-[#6c757d]" />
                <span class="text-[#495057]">{{ reservation.booking.time }}</span>
              </div>
              <div class="flex items-center gap-2 text-sm">
                <Users class="w-4 h-4 text-[#6c757d]" />
                <span class="text-[#495057]">{{ reservation.booking.partySize }}명</span>
              </div>
              <div class="flex items-start gap-2 text-sm">
                <MapPin class="w-4 h-4 text-[#6c757d] mt-0.5 flex-shrink-0" />
                <span class="text-[#495057] leading-relaxed">{{ reservation.restaurant.address }}</span>
              </div>
            </div>

            <div class="flex gap-2">
              <RouterLink :to="`/restaurant/${reservation.id}/confirmation`" class="flex-1">
                <Button
                  variant="outline"
                  class="w-full h-10 border-[#dee2e6] text-[#495057] bg-white hover:bg-[#f8f9fa] rounded-lg text-sm font-medium"
                >
                  예약 상세
                </Button>
              </RouterLink>
              <Button
                variant="outline"
                class="flex-1 h-10 border-[#dee2e6] text-[#495057] bg-white hover:bg-[#f8f9fa] rounded-lg text-sm font-medium"
              >
                예약 취소
              </Button>
            </div>
          </div>
        </Card>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
</style>
