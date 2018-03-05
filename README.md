# Focus

_A-Muse-D presents: **Focus**_

Focus is a game aimed to help you focus on what matters. By using the [Muse](http://choosemuse.com) headband
as a companion, focus the more you can to make the squares (and worries) disappear.

## How to start

1. Clone the repo
`git clone https://github.com/Muse-2018/Amuzed`
2. Import the project on Android Studio
3. Enjoy!

## Caveats

- The Muse SDK used by the application only supports devices with ARM EABI V7a devices. This means that the Muse headset functionality only works on devices from about 2010-2014. [Devices include](https://androidbycode.wordpress.com/tag/armeabi-v7a/):

    - Nexus One - Nexus 6, Nexus 7 (2012, 2013)
    - Samsung Galaxy S - S5
    - Samsung Galaxy Note - Note 4

## Potential future features

These features might be considered for a potential future release
of Focus based on received user feedback.

- Add different profiles based on a person (Difficulty)
- Add sound feedback when a square disappears (User might focus with eyes closed)
- Save brainwaves information to a external file for future usage.
- Incorporate usage of other brain wave activity (beta, theta, gamma) in API with game state
- Improve responsiveness of game based on user feedback and choice of data in Muse API

_Note: The following section is optional and is only there for basic learning about the application._

## Basic workings

### User flow

Since this project is more aimed to be an educational one for a hackathon, here's the general structure of the application. The app is structured mainly in 3 parts which are:

1. `MainActivity` which is responsible for the first screen as seen by the user. In this section, the user is either required to connect it's Muse device or to directly play the game.
2. `ConnectActivity` which is responsible for the connect screen which is required to connect the Muse headband firsthand.
3. `AndroidLauncher` which is responsible for launching Focus as a game. 

It basically looks like this. (As simple as it can really get)

`MainActivity --> ConnectActivity/AndroidLauncher`

### Communication with the Muse

All interaction with the Muse is ensured by it's [SDK](http://developer.choosemuse.com/sdk/android). Here are the main components used:

- `FocusDataListener` which is responsible for retrieving the data received from the device and process it's data. In the case of this project, it processes a certain amount given a certain time.
- `EegProcessingUtil` which is responsible as a utility class to determine the state of a user. (For example, to determine if a user is calm)
- `EegDataProcessingReceiver` which is responsible to to notify the game (`AndroidLauncher`) about the current state of a user.
