# Old Warden - Fabric 1.21.1 (GeckoLib 4.7.3)

## التشغيل
1. أنشئ مشروع Fabric من https://fabricmc.net/develop/template/ (Minecraft 1.21.1، Mojang mappings، mod id: oldwarden)
   عشان تحصل على Gradle wrapper.
2. انسخ مجلد `src/` من هذي الحزمة فوق مجلد `src/` في مشروعك، واستبدل `build.gradle` و`gradle.properties`.
3. شغّل `./gradlew runClient`، وللملف النهائي `./gradlew build` (يطلع في build/libs).
4. لازم تثبت GeckoLib 4.7.x و Fabric API في مجلد mods عند اللعب.

## الاختبار داخل اللعبة
- Creative > Spawn Eggs > Old Warden Spawn Egg
- أو: /summon oldwarden:old_warden
- طبيعياً: في الصحراء (نادر جداً، يظهر بأي إضاءة وبدون إنذار)

## ما هو مطبق
| الميزة | التفاصيل |
|---|---|
| الصحة | 1000 قلب = 2000 HP (الحد الأقصى في ماينكرافت لـ MAX_HEALTH هو 1024، فنستخدم 1024 ونقلل الضرر الداخل بنسبة 0.512) |
| الضرر | 100 (50 قلب) في الضرب القريب |
| الرؤية | FOLLOW_RANGE = 50 بلوك مع خط رؤية |
| المناعة | النار واللافا والغرق وضرر السقوط، ما يتراجع بالضرب |
| Sonic boom | فقط لما الهدف بعيد 18 بلوك أو أكثر (SONIC_MIN)، شحن 34 tick ثم شعاع يتجاوز الدرع، ضرر 60 |
| المطاردة | قريب (أقل من 18 بلوك): يلاحق ويضرب بسرعة اللاعب الراكض (0.30) |
| القفزة | فقط من 10 إلى 28 بلوك، قفزة عالية نحو اللاعب ثم ضربة أرضية بنطاق 6 بلوكات وضرر 100 |
| السبون | الصحراء فقط، 1 من 30 محاولة، واحد فقط ضمن 160 بلوك |
| الأنميشن | idle, walk, roar, attack, sonic_boom, leap, death |
| الدروب | نجمة نذر (nether star) + 2 إلى 5 echo shard |

كل الأرقام في `OldWardenConfig.java`.

## تنبيهات
- هذا الكود ما تم تجميعه (compile) عند كتابته. توقع تعديلات بسيطة في أسماء الدوال حسب إصدار GeckoLib/Fabric API.
- لو الذراعين أو الراس تتحرك معكوسة في الأنميشن، اعكس إشارة الدوران في `old_warden.animation.json`.
- الصوت من أصوات الواردن الأصلية. للصوت الخاص أضف ملفات ogg وسجلها في SoundEvents.

## تحديث v2
- يهاجم أي كائن حي (لاعبين أولاً ثم الحيوانات والقرويين والوحوش) ما عدا Old Wardens وarmor stands
- أنميشنز جديدة: run, attack_left, attack_right, slam, hurt, chest_pound
- أصوات: نبض القلب، نقرات الزوائد، أصوات القرب، غضب، طرق الصدر
