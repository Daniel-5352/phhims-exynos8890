//SPDX-License-Identifier: GPL-2.0
package me.phh.sip

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.media.AudioRecord
import android.telephony.Rlog

object SipAudioRecordRouting {
    fun pinBuiltinMic(
        logTag: String,
        context: Context,
        audioRecord: AudioRecord,
    ): AudioManager {
        val audioManager = context.getSystemService(AudioManager::class.java)

        // Check if an external headset or bluetooth communication device is present
        val inputDevices = audioManager.getDevices(AudioManager.GET_DEVICES_INPUTS)
        val headsetOrBt = inputDevices.firstOrNull {
            it.type == AudioDeviceInfo.TYPE_WIRED_HEADSET ||
                it.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO ||
                it.type == AudioDeviceInfo.TYPE_USB_HEADSET
        }

        if (headsetOrBt != null) {
            audioRecord.preferredDevice = headsetOrBt
            Rlog.d(logTag, "AudioRecord preferredDevice set to external input: type=${headsetOrBt.type} name=${headsetOrBt.productName}")
            return audioManager
        }

        // Pin capture to the built-in mic so the Samsung HAL cannot reroute it to
        // the baseband PCM path (pcmC0D110c) that produces silence for software IMS.
        val builtinMic = inputDevices
            .firstOrNull { it.type == AudioDeviceInfo.TYPE_BUILTIN_MIC }
        if (builtinMic != null) {
            audioRecord.preferredDevice = builtinMic
            Rlog.d(logTag, "AudioRecord preferredDevice set to builtin mic: id=${builtinMic.id} name=${builtinMic.productName}")
        } else {
            Rlog.w(logTag, "AudioRecord: no TYPE_BUILTIN_MIC found, proceeding without preferredDevice")
        }
        return audioManager
    }
}
