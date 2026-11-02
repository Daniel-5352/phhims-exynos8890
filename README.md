# PhhIms for Samsung Exynos 8890 

A customized fork of the open-source **PhhIms** (IMS/VoLTE framework) tailored specifically for Samsung Exynos 8890 platform devices (Galaxy S7, S7 Edge, Note FE) running **LineageOS 17.1 up to LineageOS 23.2**.

This repository contains hardware-specific audio routing fixes, customized RIL hooks, stabilize 4G/VoLTE calls on legacy Samsung Exynos chipsets.

### Compatible Devices
* **Samsung Galaxy S7** (`herolte` / `SM-G930F/FD`)
* **Samsung Galaxy S7 Edge** (`hero2lte` / `SM-G935F/FD`)
* **Samsung Galaxy Note FE** (`gracelte` / `SM-N935F/FD`)
   
### Uncompatible Devices
* **Samsung Galaxy Note 7**  (`grace` / `SM-N930F`) Due to Recalls and Network Bans

### Supported ROMs
* **LineageOS Versions:** 17.1 (Android 10) $\rightarrow$ 23.2 (Android 16)
* **AOSP-based custom ROMs**`

### Tested Carriers
* **Turkcell (Türkiye)** — (Outgoing/Incoming VoLTE calls, no 3G fallback drop).

### Issues
 * **Speakerphone**


Wait for Second like 10 or 7 seconds for the VoLTE, IMS to Start When you see "4G" text in the Signal bar
