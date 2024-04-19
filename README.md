# PetBook
An App for pet lovers to get a daily feed of pet information. Initial Version of this app supports feed for cats. Users can use the favorite feature if they chose to do it. Details screen provides more information about the pet and users get an ability to open wikipedia in a webview inside app as well.

1)The app has a splash screen suitable for the app. It is currently a static launch screen for android 12 & above.

2)The app has a custom launcher icon.

3)All features in the app are currently completed. 

4)The app has one screen with a list of cat feeds using LazyColumn and LazyRow for portrait and landscape views

5)Each item(cat) in the list shows the breed name, origin, and an image of the cat, and the text are styled appropriately.

6)Tapping an item in this list navigate to a detail view: This shows the same data in the list item with some further details such as a longer description, bigger picture, button to open wikipedia page etc

7)Included enough items(50) to ensure that the user can scroll the list to see all the items in it. 

8)The app has one network call using library like Retrofit to download data that relate to the core tasks of the app.

9)The app’s repo does not contain API keys or other authentication information. The app uses secrets-gradle-platform. Please contact me to get the api key for the local.properties file

10)The api key has a 10K requests per month limit.

11)The app handles all typical errors related to network calls — including server error response codes and no network connection — and keeps the user informed.  

12)The app uses PreferenceDataStore to save user settings for the favorites feature.

13)The app uses Kotlin Coroutines like viewModelScope appropriately to keep slow-running tasks off the main thread and to update the UI on the main thread.

14)The app communicates to the user whenever data is missing or empty. The app has appropriate messages to inform users.

15)All included screens work successfully without crashes or UI issues for a normal user behaviour.

16)Views work for both landscape and portrait orientations, for the wide range of common Android devices (mostly targeting Pixel & Samsung devices and up-to-date emulators running the latest Android OS).

17)App works on the latest 5 versions of Android OS. 

18)Views work for both light and dark modes. View is consistent across both modes.

19)There are no obvious UI issues, like UI components overlapping or running off the screen.

20)The code is organized and easily readable.

21)Project source files are organized in packages such as ui.components, ui.screens, models, networking etc.

23)Composables are be in their own files, smaller sub-component composables (not screen level composables) also each have their own Preview functions as well.
The project uses MVVM architecture: The viewmodel includes only one StateFlow object that returns a “stateful” representation of the associated data (loading, error, success) typically in the form of a sealed class. Networking code is in a service interface (following the Retrofit pattern).

24)The project has a test plan for unit tests, with a minimum of 50% code coverage for Methods, and all tests succeed. 
Classes covered:
ViewModels = >50%
Repositories = 100%
Adapters > 80%

25)A custom app icon.

26)At least 6 screens (for example: splash, list, detail, about, favorites, settings)

27)A custom app name : PetBook

28)Multiple compose animations for favoriting the pet, Animated text in details screen, animation while tapping on the picture to shrink/expand

