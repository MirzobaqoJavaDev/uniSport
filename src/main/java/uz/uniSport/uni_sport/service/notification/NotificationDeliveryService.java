package uz.uniSport.uni_sport.service.notification;

import uz.uniSport.uni_sport.domain.notification.NotificationChannel;

public interface NotificationDeliveryService {

    /**
     * Eslatma xabarini ko'rsatilgan kanal orqali yuborish.
     * Hozirda bu metod uchinchi tomon xizmati yo'qligi sababli faqat logga yozadi.
     *
     * @param phoneNumber Yoki fcm token.
     * @param message Yuboriladigan xabar.
     * @param channel SMS yoki PUSH.
     * @return muvaffaqiyatli yuborilgan bo'lsa true, aks holda false.
     */
    boolean sendNotification(String phoneNumber, String message, NotificationChannel channel);
    
}
