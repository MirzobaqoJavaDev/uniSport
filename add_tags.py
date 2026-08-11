import os
import re

tags_map = {
    'AttendanceController.java': {'name': 'Davomat', 'desc': 'Talabalarning jismoniy tarbiya darslariga davomatini boshqarish'},
    'ClassScheduleController.java': {'name': 'Dars Jadvali', 'desc': 'Jismoniy tarbiya darslari jadvalini yaratish va ko\'rish'},
    'PEClassController.java': {'name': 'Jismoniy Tarbiya Darslari', 'desc': 'Jismoniy tarbiya guruhlari va darslarni boshqarish'},
    'ExerciseController.java': {'name': 'Mashqlar', 'desc': 'Sport mashqlari ro\'yxati va ularning ma\'lumotlarini boshqarish'},
    'WorkoutPlanController.java': {'name': 'Mashg\'ulotlar Rejasi', 'desc': 'Foydalanuvchilarning shaxsiy mashg\'ulot rejalarini tuzish va boshqarish'},
    'AuthController.java': {'name': 'Autentifikatsiya', 'desc': 'Tizimga kirish, ro\'yxatdan o\'tish va tokenlarni boshqarish'},
    'UserController.java': {'name': 'Foydalanuvchilar', 'desc': 'Foydalanuvchilar profillari va ma\'lumotlarini boshqarish'},
    'PermissionController.java': {'name': 'Huquqlar', 'desc': 'Tizimdagi rol va huquqlarni boshqarish'},
    'DynamicQRCodeController.java': {'name': 'QR Kodlar', 'desc': 'Dinamik QR kodlar yaratish va tekshirish'},
    'SubscriptionController.java': {'name': 'Obunalar', 'desc': 'Foydalanuvchilarning sport to\'garaklariga obunalarini boshqarish'},
    'SubscriptionPlanController.java': {'name': 'Obuna Rejalari', 'desc': 'Pullik obuna tariflari va rejalarini boshqarish'},
    'PaymentController.java': {'name': 'To\'lovlar', 'desc': 'To\'lov tranzaksiyalari va xizmatlarni sotib olishni boshqarish'},
    'TokenUsageLogController.java': {'name': 'AI Tokenlar Sarfi', 'desc': 'Sun\'iy intellekt xizmatlari uchun sarflangan tokenlar hisobini yuritish'},
    'AIController.java': {'name': 'Sun\'iy Intellekt', 'desc': 'AI yordamchisi va neyrotarmoqlar orqali xizmatlar ko\'rsatish'},
    'FileController.java': {'name': 'Fayllar', 'desc': 'Tizimga rasm, video va boshqa fayllarni yuklash hamda ularni ko\'rish'},
    'CourtController.java': {'name': 'Sport Kortlari', 'desc': 'Stadion va sport maydonchalarini boshqarish'},
    'InventoryItemController.java': {'name': 'Invertar', 'desc': 'Zaldagi jihozlar va sport ashyolarini hisobga olish'},
    'QuotaController.java': {'name': 'Kvotalar', 'desc': 'Zal yoki darslarga qatnashuvchilar soni chegaralarini boshqarish'},
    'FacilityController.java': {'name': 'Sport Inshootlari', 'desc': 'Sport majmualari, zallar va inshootlarni boshqarish'},
    'BookingController.java': {'name': 'Band qilish', 'desc': 'Sport maydonchalarini band qilish va bronlarni boshqarish'},
    'MealLogController.java': {'name': 'Ovqatlanish', 'desc': 'Kunlik kaloriya va ovqatlanish ratsionini hisobga olish'},
    'RentalController.java': {'name': 'Ijaralar', 'desc': 'Sport jihozlarini ijaraga berish va qaytarish jarayonlarini boshqarish'},
    'EquipmentController.java': {'name': 'Uskunalar', 'desc': 'Zaldagi jihozlar va trenajyorlarni ro\'yxatga olish'}
}

base_path = 'src/main/java/uz/uniSport/uni_sport/controller'

for root, dirs, files in os.walk(base_path):
    for filename in files:
        if filename in tags_map:
            filepath = os.path.join(root, filename)
            with open(filepath, 'r') as file:
                content = file.read()
            
            if '@Tag' not in content:
                # Add import if missing
                if 'import io.swagger.v3.oas.annotations.tags.Tag;' not in content:
                    content = re.sub(r'(import [^;]+;)', r'\1\nimport io.swagger.v3.oas.annotations.tags.Tag;', content, count=1)
                
                # Add @Tag annotation
                tag_info = tags_map[filename]
                tag_annotation = f'@Tag(name = "{tag_info["name"]}", description = "{tag_info["desc"]}")\n@RestController'
                content = content.replace('@RestController', tag_annotation)
                
                with open(filepath, 'w') as file:
                    file.write(content)
                print(f"Added Tag to {filename}")
