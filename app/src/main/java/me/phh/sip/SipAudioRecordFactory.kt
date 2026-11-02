//SPDX-License-Identifier: GPL-2.0
package me.phh.sip

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.telephony.Rlog

object SipAudioRecordFactory {
    private const val TAG = "PHH SipAudioRecordFactory"

    fun createVoiceCommunicationRecord(
        bufferSize: Int,
        audioCodec: NegotiatedAudioCodec = SipAudioCodecs.AMR_NB,
    ): AudioRecord {
        val commRecord = try {
            AudioRecord(
                MediaRecorder.AudioSource.VOICE_COMMUNICATION,
                audioCodec.sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize,
            )
        } catch (t: Throwable) {
            Rlog.w(TAG, "AudioRecord creation failed for VOICE_COMMUNICATION", t)
            null
        }

        if (commRecord != null && commRecord.state == AudioRecord.STATE_INITIALIZED) {
            return commRecord
        }

        Rlog.w(TAG, "VOICE_COMMUNICATION AudioRecord uninitialized; falling back to MIC source for Exynos/legacy audio HAL")
        commRecord?.release()

        val micRecord = try {
            AudioRecord(
                MediaRecorder.AudioSource.MIC,
                audioCodec.sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize,
            )
        } catch (t: Throwable) {
            Rlog.e(TAG, "AudioRecord creation failed for MIC fallback", t)
            null
        }

        if (micRecord != null && micRecord.state == AudioRecord.STATE_INITIALIZED) {
            return micRecord
        }

        micRecord?.release()

        return AudioRecord(
            MediaRecorder.AudioSource.DEFAULT,
            audioCodec.sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize,
        )
    }

    fun minBufferSize(
        audioCodec: NegotiatedAudioCodec = SipAudioCodecs.AMR_NB,
    ): Int =
        AudioRecord.getMinBufferSize(
            audioCodec.sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
        )
}
