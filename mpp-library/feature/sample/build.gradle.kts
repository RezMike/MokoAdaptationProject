plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.mokoResources)
}

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    androidMainImplementation(Deps.Libs.Android.lifecycle)

    commonMainImplementation(Deps.Libs.MultiPlatform.mokoResources.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoMvvm.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoUnits.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoFields.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoPaging.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoPermissions.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoMedia.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoGeo.common)
    androidMainImplementation(Deps.Libs.Android.googleServicesLocation)
}

multiplatformResources {
    multiplatformResourcesPackage = "org.example.library.feature.sample"
}