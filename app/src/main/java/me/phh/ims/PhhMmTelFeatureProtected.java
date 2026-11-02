package me.phh.ims;

import android.telephony.ims.feature.MmTelFeature;
import android.telephony.ims.feature.ImsFeature;
import android.telephony.ims.feature.CapabilityChangeRequest;
import android.telephony.Rlog;
import android.telephony.ims.stub.ImsRegistrationImplBase;

import java.util.List;

public class PhhMmTelFeatureProtected extends MmTelFeature {

    public interface CapabilityChangeCallback {
        void onChangeCapabilityConfigurationError(int capability, int radioTech, int reason);
    }

    private final static String TAG = "Phh MmTelFeatureProtected";
    private int slotId;
    private int capabilities;

    public PhhMmTelFeatureProtected(int slotId) {
        super(Runnable::run);
        this.slotId = slotId;
        this.capabilities =
            MmTelCapabilities.CAPABILITY_TYPE_VOICE |
            MmTelCapabilities.CAPABILITY_TYPE_SMS;
    }

    @Override
    public void changeEnabledCapabilities(
            CapabilityChangeRequest request,
            CapabilityCallbackProxy callback) {
        onChangeEnabledCapabilities(request, callback);
    }

    protected void onChangeEnabledCapabilities(
            CapabilityChangeRequest request,
            CapabilityCallbackProxy callback) {
        for (CapabilityChangeRequest.CapabilityPair pair :
                request.getCapabilitiesToEnable()) {
            callback.onChangeCapabilityConfigurationError(
                    pair.getCapability(),
                    pair.getRadioTech(),
                    android.telephony.ims.feature.ImsFeature.CAPABILITY_SUCCESS);
        }
        for (CapabilityChangeRequest.CapabilityPair pair :
                request.getCapabilitiesToDisable()) {
            callback.onChangeCapabilityConfigurationError(
                    pair.getCapability(),
                    pair.getRadioTech(),
                    android.telephony.ims.feature.ImsFeature.CAPABILITY_SUCCESS);
        }
    }
}
