/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    // 5. Apply plugin
    plugin(Deps.Plugins.mokoResources)
    plugin(Deps.Plugins.iosFramework)
}

val mppLibs = listOf(
    Deps.Libs.MultiPlatform.multiplatformSettings,
    Deps.Libs.MultiPlatform.napier,
    // 6. Add dependency
    Deps.Libs.MultiPlatform.mokoResources,
    Deps.Libs.MultiPlatform.mokoParcelize,
    Deps.Libs.MultiPlatform.mokoMvvm,
    Deps.Libs.MultiPlatform.mokoUnits,
    Deps.Libs.MultiPlatform.mokoFields
)
val mppModules = listOf(
    Deps.Modules.domain,
    Deps.Modules.Feature.config,
    Deps.Modules.Feature.list,
    Deps.Modules.Feature.sample
)

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines) {
        // we should force native-mt version for ktor 1.4.0 on iOS
        isForce = true
    }

    androidMainImplementation(Deps.Libs.Android.lifecycle)

    mppLibs.forEach { commonMainApi(it.common) }
    mppModules.forEach { commonMainApi(project(it.name)) }
}

// 7. Configure plugin
multiplatformResources {
    multiplatformResourcesPackage = "org.example.library"
    iosBaseLocalizationRegion = "en" // default, may be omitted
    multiplatformResourcesSourceSet = "commonMain" // default, may be omitted
}

framework {
    mppModules.forEach { export(it) }
    mppLibs.forEach { export(it) }
}
