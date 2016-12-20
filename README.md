# FRC Scouting App

Team 25's base scouting app for the 2017 FRC season and beyond

Key features: 

* Material design UI and easy data input and verification
* Data pulled from [The Blue Alliance](http://www.thebluealliance.com/) used to auto-fill match data
* Easy JSON data export to other Android devices and PCs
* Merge data from other devices

##To Do
**Major Additions/Changes**
* Finish `DataDownloader` class with methods to download data and return as a JSON string
  * Alliances and teams for each match
  * Teams at each event
  * Events that Team 25 is attending for the season
* Find a method to store the data from the TBA and write classes (SQLite database?)
 * Import/export said data via Bluetooth from/to other devices
* Import and merge JSON scout data from other devices in-app
* Automate "Current event" `ListPreference` with events for the season
* Improve speed of saving match data by writing each `ScoutEntry` object individually, without loading the existing data as an `ArrayList`
 * Initialize file with an opening bracket
 * After each entry is written as JSON, write a comma to the file
 * When exporting data, create a temporary file with the closing bracket to complete the JSON array (`]`)

**Minor Changes**
 * Integrate a Preference item to download the data from TBA
 * Integrate the "Import match data" Preference item
 * Create animations between fragments
 * Improve UI (less clutter) in quick comments section
 * "Materialize" main menu icons
 * Fix button sizes on mobile
