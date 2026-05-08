package net.masuno.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.MathUtility;
import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.client.multiplayer.ClientLevel;

@Environment(EnvType.CLIENT)
public class PlayerAttackManager {
   public static List<Item> DIAMOND_ARMOR = new ArrayList<>();
   public static List<Item> NETHERITE_ARMOR = new ArrayList<>();

   public static void clientAttack(Player player, Entity victim, Level world) {
      if (world.isClientSide()) {
         if (!MasConfig.INSTANCE.ShieldEffect) {
            return;
         }

         if (!player.getMainHandItem().is(Items.MACE)) {
            return;
         }

         boolean is_shielding = false;
         if (victim instanceof Player victim_player && MathUtility.isShielding(player, victim_player) && player.fallDistance >= 1.5) {
            is_shielding = true;
            ShieldShockwave(victim_player);
         }

         if (!is_shielding
                 && victim instanceof LivingEntity livingEntity
                 && hasEnchantment(player.getMainHandItem(), Enchantments.BREACH)
                 && player.fallDistance > 1.5
                 && MasConfig.INSTANCE.ArmorParticles) {
            ArmorParticles(livingEntity);
         }
      }
   }

   public static void ShieldShockwave(Player shielder) {
      Vec3 direction = shielder.getLookAngle();
      Vec3 height = new Vec3(0.0, shielder.getDimensions(shielder.getPose()).height() * 0.6F, 0.0);
      Vec3 pos = shielder.position().add(height).add(direction.scale(0.5));
      shielder.level().addParticle(ModParticles.SHIELD_WAVE, pos.x(), pos.y(), pos.z(), 0.0, 0.0, 0.0);
   }

   public static void SlamEffect(double x, double y, double z, double size) {
      ClientLevel world = Minecraft.getInstance().level;
      if (world != null) {
         size = Math.clamp(size, 1.0, 10.0);
         Random rand = new Random();
         Vec3 r = new Vec3(rand.nextFloat(-0.6F, 0.6F), rand.nextFloat(-1.2F, 1.2F), rand.nextFloat(-0.6F, 0.6F));
         if (MasConfig.INSTANCE.MaceShockwave) {
            world.addParticle(ModParticles.SHOCKWAVE, x, y, z, 0.8, 0.8, 1.5 * MasConfig.INSTANCE.MaceShockwaveSize * size);
            world.addParticle(ModParticles.SHOCKWAVE, x, y, z, 0.4, 1.0, 0.35 * MasConfig.INSTANCE.MaceShockwaveSize * size);
            world.addParticle(ModParticles.WINDWAVE, x, y, z, 0.8, 0.8, 1.75 * MasConfig.INSTANCE.MaceShockwaveSize * size);
         }

         if (MasConfig.INSTANCE.MaceSpark) {
            world.addParticle(ModParticles.FLICK, x + r.x, y + r.y + 0.9F, z + r.z, 0.5, 0.0, 0.0);
            world.addParticle(ModParticles.FLICK, x + r.x, y + r.y + 0.9F, z + r.z, 0.2F, 0.0, 0.0);
         }

         if (MasConfig.INSTANCE.MaceFlash) {
            world.addParticle(ModParticles.FLASH, x, y, z, 0.0, 0.0, 0.0);
         }
      }
   }

   public static void SpawnParticlesOnHitbox(Level world, Vec3 pos, Vec3 size, ParticleOptions particle, int count) {
      for (int i = 0; i <= count; i++) {
         Vec3 r = new Vec3(
                 new Random().nextDouble(-size.x / 2.0, size.x / 2.0),
                 new Random().nextDouble(0.0, size.y),
                 new Random().nextDouble(-size.x / 2.0, size.x / 2.0)
         );
         world.addParticle(
                 particle,
                 pos.x() + r.x(),
                 pos.y() + r.y(),
                 pos.z() + r.z(),
                 r.x(),
                 r.y(),
                 r.z()
         );
      }
   }

   public static boolean hasEnchantment(ItemStack stack, ResourceKey<Enchantment> enchantment) {
      return stack.getEnchantments().keySet().toString().contains(enchantment.identifier().toString());
   }

   public static void ArmorParticles(LivingEntity le) {
      List<ItemStack> armor_items = new ArrayList<>();
      DIAMOND_ARMOR.add(Items.DIAMOND_HELMET);
      DIAMOND_ARMOR.add(Items.DIAMOND_CHESTPLATE);
      DIAMOND_ARMOR.add(Items.DIAMOND_LEGGINGS);
      DIAMOND_ARMOR.add(Items.DIAMOND_BOOTS);
      NETHERITE_ARMOR.add(Items.NETHERITE_HELMET);
      NETHERITE_ARMOR.add(Items.NETHERITE_CHESTPLATE);
      NETHERITE_ARMOR.add(Items.NETHERITE_LEGGINGS);
      NETHERITE_ARMOR.add(Items.NETHERITE_BOOTS);
      armor_items.add(le.getItemBySlot(EquipmentSlot.HEAD));
      armor_items.add(le.getItemBySlot(EquipmentSlot.CHEST));
      armor_items.add(le.getItemBySlot(EquipmentSlot.LEGS));
      armor_items.add(le.getItemBySlot(EquipmentSlot.FEET));

      for (ItemStack item : armor_items) {
         Vec3 size = new Vec3(
                 le.getDimensions(le.getPose()).width(),
                 le.getDimensions(le.getPose()).height() / 4.0F,
                 le.getDimensions(le.getPose()).width()
         );
         if (item.is(ItemTags.HEAD_ARMOR)) {
            int count = new Random().nextInt(0, 3);
            if (DIAMOND_ARMOR.contains(item.getItem())) {
               SpawnParticlesOnHitbox(
                       le.level(), le.position().add(new Vec3(0.0, size.y() * 3.0, 0.0)), size, ModParticles.DIAMOND_SCRAP, count
               );
               continue;
            }

            if (NETHERITE_ARMOR.contains(item.getItem())) {
               SpawnParticlesOnHitbox(
                       le.level(),
                       le.position().add(new Vec3(0.0, size.y() * 3.0, 0.0)),
                       size,
                       ModParticles.NETHERITE_SCRAP,
                       count
               );
               continue;
            }
         }

         if (item.is(ItemTags.CHEST_ARMOR)) {
            int count = new Random().nextInt(0, 3);
            if (DIAMOND_ARMOR.contains(item.getItem())) {
               SpawnParticlesOnHitbox(
                       le.level(), le.position().add(new Vec3(0.0, size.y() * 2.0, 0.0)), size, ModParticles.DIAMOND_SCRAP, count
               );
               continue;
            }

            if (NETHERITE_ARMOR.contains(item.getItem())) {
               SpawnParticlesOnHitbox(
                       le.level(),
                       le.position().add(new Vec3(0.0, size.y() * 2.0, 0.0)),
                       size,
                       ModParticles.NETHERITE_SCRAP,
                       count
               );
               continue;
            }
         }

         if (item.is(ItemTags.LEG_ARMOR)) {
            int count = new Random().nextInt(0, 3);
            if (DIAMOND_ARMOR.contains(item.getItem())) {
               SpawnParticlesOnHitbox(
                       le.level(), le.position().add(new Vec3(0.0, size.y() * 3.0, 0.0)), size, ModParticles.DIAMOND_SCRAP, count
               );
               continue;
            }

            if (NETHERITE_ARMOR.contains(item.getItem())) {
               SpawnParticlesOnHitbox(
                       le.level(),
                       le.position().add(new Vec3(0.0, size.y() * 3.0, 0.0)),
                       size,
                       ModParticles.NETHERITE_SCRAP,
                       count
               );
               continue;
            }
         }

         if (item.is(ItemTags.FOOT_ARMOR)) {
            int count = new Random().nextInt(0, 3);
            if (DIAMOND_ARMOR.contains(item.getItem())) {
               SpawnParticlesOnHitbox(le.level(), le.position(), size, ModParticles.DIAMOND_SCRAP, count);
            } else if (NETHERITE_ARMOR.contains(item.getItem())) {
               SpawnParticlesOnHitbox(le.level(), le.position(), size, ModParticles.NETHERITE_SCRAP, count);
            }
         }
      }
   }
}
