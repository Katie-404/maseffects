package net.masuno.config;

import net.masuno.MasEffects;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.uku3lig.ukulib.config.option.*;
import net.uku3lig.ukulib.config.option.widget.ButtonTab;
import net.uku3lig.ukulib.config.screen.TabbedConfigScreen;

import java.util.Optional;

public class MasConfigScreen extends TabbedConfigScreen<MasConfig> {
    public MasConfigScreen(Screen parent){
        super("maseffects.config.title", parent, MasEffects.manager);
    }
    @Override
    protected Tab[] getTabs(MasConfig config) {
        return new Tab[]{new generalConfig(), new MaceConfig(), new HitboxConfig()};
    }
    public class generalConfig extends ButtonTab<MasConfig>
    {
        public generalConfig(){
            super("maseffects.config.general", MasConfigScreen.this.manager);
        }
        @Override
        public WidgetCreator[] getWidgets(MasConfig config){
            return new WidgetCreator[]{
                    CyclingOption.ofBoolean("maseffects.option.windparticles", config.WindParticles, (v) -> config.WindParticles = v),
                    CyclingOption.ofBoolean("maseffects.options.customtotemeffect", config.CustomTotemEffect, (v)-> config.CustomTotemEffect = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.customtotemeffect"))),
                    new TypedInputOption<Float>(
                            "maseffects.option.totemeffectopacity",
                            String.valueOf(config.getTotemEffectOpacity()),
                            config::setTotemEffectOpacity,
                            this::convert,
                            v -> v > 0
                    ),
                    CyclingOption.ofBoolean("maseffects.option.playerdeatheffect", config.PlayerDeathEffect, (v)-> config.PlayerDeathEffect = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.playerdeatheffect"))),
                    CyclingOption.ofBoolean("maseffects.option.pearltrailparticles", config.PearlTrailParticles, (v)-> config.PearlTrailParticles = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.pearltrailparticles"))),
                    new TypedInputOption<Float>(
                            "maseffects.option.pearltrailopacity",
                            String.valueOf(config.getPearlTrailOpacity()),
                            config::setPearlTrailOpacity,
                            this::convert,
                            v -> v > 0
                    ),
                    CyclingOption.ofBoolean("maseffects.options.glideicon", config.GlideIcon, (v)-> config.GlideIcon = v),
            };
        }
        private Optional<Float> convert(String value) {
            try{
                return Optional.of(Float.parseFloat(value));
            }catch (Exception e) {
                return Optional.empty();
            }
        }
    }
    public class MaceConfig extends ButtonTab<MasConfig>
    {
        public MaceConfig() {
            super("maseffects.config.mace", MasConfigScreen.this.manager);
        }
        @Override
        public WidgetCreator[] getWidgets(MasConfig config){
            return new WidgetCreator[]{
                    CyclingOption.ofBoolean("maseffects.option.maceshowckwave", config.MaceShockwave, (v) -> config.MaceShockwave = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.maceshowckwave"))),
                    new TypedInputOption<Float>(
                            "maseffects.option.maceshockwavesize",
                            String.valueOf(config.getMaceShockwaveSize()),
                            config::setMaceShockwaveSize,
                            this::convert,
                            v -> v > 0
                    ),
                    new TypedInputOption<Float>(
                            "maseffects.option.maceshockwaveopacity",
                            String.valueOf(config.getMaceShockwaveOpacity()),
                            config::setMaceShockwaveOpacity,
                            this::convert,
                            v -> v > 0
                    ),
                    CyclingOption.ofBoolean("maseffects.option.macespark", config.MaceSpark, (v) -> config.MaceSpark = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.macespark"))),
                    CyclingOption.ofBoolean("maseffects.option.maceflash", config.MaceFlash, (v) -> config.MaceFlash = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.maceflash"))),
                    CyclingOption.ofBoolean("maseffects.option.shieldeffect", config.ShieldEffect ,(v) -> config.ShieldEffect = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.shieldeffect"))),
                    CyclingOption.ofBoolean("maseffects.option.armorparticles", config.ArmorParticles ,(v) -> config.ArmorParticles = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.armorparticles"))),
                    CyclingOption.ofBoolean("maseffects.option.legacyshockwave", config.LegacyMaceShockwave ,(v) -> config.LegacyMaceShockwave = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.legacyshockwave"))),
            };

        }
        private Optional<Float> convert(String value) {
            try{
                return Optional.of(Float.parseFloat(value));
            }catch (Exception e) {
                return Optional.empty();
            }
        }
    }
    public class HitboxConfig extends ButtonTab<MasConfig>
    {
        public HitboxConfig(){
            super("maseffects.config.hitbox", MasConfigScreen.this.manager);
        }
        @Override
        public WidgetCreator[] getWidgets(MasConfig config){
            return new WidgetCreator[]{
                    CyclingOption.ofBoolean("maseffects.option.customhitbox", config.CustomHitbox, (v) -> config.CustomHitbox = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.customhitbox"))),
                    CyclingOption.ofBoolean("maseffects.option.pearlhitboxcolors", config.PearlHitboxColors, (v) -> config.PearlHitboxColors = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.pearlhitboxcolors"))),
                    //TODO: ally array somehow.
                    new ColorOption("maseffects.options.selfpearlcolor", config.getSelfPearlColor(), config::setSelfPearlColor),
                    new ColorOption("maseffects.options.allypearlcolor", config.getAllyPearlColor(), config::setAllyPearlColor),
                    new ColorOption("maseffects.option.otherpearlcolor", config.getOtherPearlColor(), config::setOtherPearlColor ),
                    new TypedInputOption<Float>("maseffects.option.mobpearlhitboxopacity", String.valueOf(config.getMobHitboxOpacity()), config::setMobHitboxOpacity, this::convert, v -> v > 0),
                    new TypedInputOption<Float>("maseffects.option.playerpearlhitboxopacity", String.valueOf(config.getPlayerHitboxOpacity()), config::setPlayerHitboxOpacity, this::convert, v -> v > 0),
                    new TypedInputOption<Float>("maseffects.option.hitboxfadedistance", String.valueOf(config.getHitboxFadeDistance()), config::setHitboxFadeDistance, this::convert, v -> v > 0),
                    new TypedInputOption<Float>("maseffects.option.hitboxprojectilefadedistance", String.valueOf(config.getHitboxProjectileFadeDistance()), config::setHitboxProjectileFadeDistance, this::convert, v -> v > 0),
            };
        }
        private Optional<Float> convert(String value) {
            try{
                return Optional.of(Float.parseFloat(value));
            }catch (Exception e) {
                return Optional.empty();
            }
        }
    }
}