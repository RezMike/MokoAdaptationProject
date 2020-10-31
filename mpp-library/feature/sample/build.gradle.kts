plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
}

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    androidMainImplementation(Deps.Libs.Android.lifecycle)

    commonMainImplementation(Deps.Libs.MultiPlatform.mokoResources.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoMvvm.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoFields.common)
}