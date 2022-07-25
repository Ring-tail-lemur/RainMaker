/// <reference types="node" />
import { PeerCertificate } from "tls";
import { Socket } from "net";
import { Session } from "./session";
import { Sender, SenderOptions } from "./sender";
import { Receiver, ReceiverOptions } from "./receiver";
import { Container } from "./container";
import { AbortSignalLike } from "./util/utils";
import { ConnectionEvents, ConnectionOptions as RheaConnectionOptions, Connection as RheaConnection, AmqpError, Dictionary, ConnectionError } from "rhea";
import { OnAmqpEvent } from "./eventContext";
import { Entity } from "./entity";
import { AwaitableSender, AwaitableSenderOptions } from "./awaitableSender";
/**
 * Describes the options that can be provided while creating an AMQP sender. One can also provide
 * a session if it was already created.
 * @interface SenderOptionsWithSession
 */
export interface CreateSenderOptions extends SenderOptions {
    session?: Session;
    /**
     * A signal used to cancel the Connection.createSender() operation.
     */
    abortSignal?: AbortSignalLike;
}
/**
 * Describes the options that can be provided while creating an Async AMQP sender.
 * One can also provide a session if it was already created.
 */
export interface CreateAwaitableSenderOptions extends AwaitableSenderOptions {
    session?: Session;
    /**
     * A signal used to cancel the Connection.createAwaitableSender() operation.
     */
    abortSignal?: AbortSignalLike;
}
/**
 * Describes the options that can be provided while creating an AMQP receiver. One can also provide
 * a session if it was already created.
 */
export interface CreateReceiverOptions extends ReceiverOptions {
    session?: Session;
    /**
     * A signal used to cancel the Connection.createReceiver() operation.
     */
    abortSignal?: AbortSignalLike;
}
/**
 * Describes the options that can be provided while creating an AMQP Request-Response link. One can also provide
 * a session if it was already created.
 */
export interface CreateRequestResponseLinkOptions {
    session?: Session;
    /**
     * A signal used to cancel the Connection.createRequestResponseLink() operation.
     */
    abortSignal?: AbortSignalLike;
}
/**
 * Set of options to use when running Connection.open()
 */
export interface ConnectionOpenOptions {
    /**
     * A signal used to cancel the Connection.open() operation.
     */
    abortSignal?: AbortSignalLike;
}
/**
 * Set of options to use when running Connection.close()
 */
export interface ConnectionCloseOptions {
    /**
     * A signal used to cancel the Connection.close() operation.
     */
    abortSignal?: AbortSignalLike;
}
/**
 * Set of options to use when running Connection.createSession()
 */
export interface SessionCreateOptions {
    /**
     * A signal used to cancel the Connection.createSession() operation.
     */
    abortSignal?: AbortSignalLike;
}
/**
 * Describes the options that can be provided while creating an AMQP connection.
 * @interface ConnectionOptions
 */
export interface ConnectionOptions extends RheaConnectionOptions {
    /**
     * @property {number} [operationTimeoutInSeconds] - The duration in which the promise should
     * complete (resolve/reject). If it is not completed, then the Promise will be rejected after
     * timeout occurs. Default: `60 seconds`.
     */
    operationTimeoutInSeconds?: number;
    /**
     * @property {Object} [webSocketOptions] - Options that include a web socket constructor along
     * with arguments to be passed to the function returned by rhea.websocket_connect()
     * This is required when the connection needs to use web sockets but is being created without
     * creating a container first. If a container is already available, use `websocket_connect` on it
     * directly instead.
     */
    webSocketOptions?: {
        /**
         * @property {any} [webSocket] - The WebSocket constructor used to create an AMQP
         * connection over a WebSocket.
         */
        webSocket: any;
        /**
         * @property {string} [url] - Websocket url which will be passed to the function returned by
         * rhea.websocket_connect()
         */
        url: string;
        /**
         * @property {string[]} {protocol} - Websocket SubProtocol to be passed to the function
         * returned by rhea.websocket_connect()
         */
        protocol: string[];
        /***
         * @property {any} {options} - Options to be passed to the function returned by
         * rhea.websocket_connect()
         */
        options?: any;
    };
}
/**
 * Describes the options that can be provided while creating a rhea-promise connection from an
 * already created rhea connection object.
 * @interface CreatedRheaConnectionOptions
 */
export interface CreatedRheaConnectionOptions {
    /**
     * @property {number} [operationTimeoutInSeconds] - The duration in which the promise should
     * complete (resolve/reject). If it is not completed, then the Promise will be rejected after
     * timeout occurs. Default: `60 seconds`.
     */
    operationTimeoutInSeconds?: number;
    /**
     * @property rheaConnection The connection object from rhea
     */
    rheaConnection: RheaConnection;
    /**
     * @property container The Container object from this (rhea-promise) library.
     */
    container: Container;
}
/**
 * Provides a sender and a receiver link on the same session. It is useful while constructing a
 * request/response link.
 *
 * @interface ReqResLink
 */
export interface ReqResLink {
    /**
     * @property {Sender} sender - The sender link for sending a request.
     */
    sender: Sender;
    /**
     * @property {Receiver} receiver - The receiver link for receiving a response.
     */
    receiver: Receiver;
    /**
     * @property {Session} session - The underlying session on whicn the sender and receiver links
     * exist.
     */
    session: Session;
}
/**
 * Describes the event listeners that can be added to the Connection.
 * @interface Connection
 */
export declare interface Connection {
    on(event: ConnectionEvents, listener: OnAmqpEvent): this;
}
/**
 * Describes the AMQP Connection.
 * @class Connection
 */
export declare class Connection extends Entity {
    /**
     * @property {ConnectionOptions} options Options that can be provided while creating the
     * connection.
     */
    options: ConnectionOptions;
    /**
     * @property {Container} container The underlying Container instance on which the connection
     * exists.
     */
    readonly container: Container;
    /**
     * @property {RheaConnection} _connection The connection object from rhea library.
     * @private
     */
    private _connection;
    /**
     * Creates an instance of the Connection object.
     * @constructor
     * @param {Connection} _connection The connection object from rhea library.
     */
    constructor(options?: ConnectionOptions | CreatedRheaConnectionOptions);
    /**
     * @property {string} id Returns the connection id.
     * @readonly
     */
    readonly id: string;
    /**
     * @property {Dictionary<any> | undefined} [properties] Provides the connection properties.
     * @readonly
     */
    readonly properties: Dictionary<any> | undefined;
    /**
     * @property {number | undefined} [maxFrameSize] Provides the max frame size.
     * @readonly
     */
    readonly maxFrameSize: number | undefined;
    /**
     * @property {number | undefined} [idleTimeout] Provides the idle timeout for the connection.
     * @readonly
     */
    readonly idleTimeout: number | undefined;
    /**
     * @property {number | undefined} [channelMax] Provides the maximum number of channels supported.
     * @readonly
     */
    readonly channelMax: number | undefined;
    /**
     * @property {AmqpError | Error | undefined} [error] Provides the last error that occurred on the
     * connection.
     */
    readonly error: AmqpError | Error | undefined;
    /**
     * Removes the provided session from the internal map in rhea.
     * Also removes all the event handlers added in the rhea-promise library on the provided session.
     * @param {Session} session The session to be removed.
     */
    removeSession(session: Session): void;
    /**
     * Creates a new amqp connection.
     * @param options A set of options including a signal used to cancel the operation.
     * @return {Promise<Connection>} Promise<Connection>
     * - **Resolves** the promise with the Connection object when rhea emits the "connection_open" event.
     * - **Rejects** the promise with an AmqpError when rhea emits the "connection_close" event
     * while trying to establish an amqp connection or with an AbortError if the operation was cancelled.
     */
    open(options?: ConnectionOpenOptions): Promise<Connection>;
    /**
     * Closes the amqp connection.
     * @param options A set of options including a signal used to cancel the operation.
     * When the abort signal in the options is fired, the local endpoint is closed.
     * This does not guarantee that the remote has closed as well. It only stops listening for
     * an acknowledgement that the remote endpoint is closed as well.
     * @return {Promise<void>} Promise<void>
     * - **Resolves** the promise when rhea emits the "connection_close" event.
     * - **Rejects** the promise with an AmqpError when rhea emits the "connection_error" event while
     * trying to close an amqp connection or with an AbortError if the operation was cancelled.
     */
    close(options?: ConnectionCloseOptions): Promise<void>;
    /**
     * Determines whether the connection is open.
     * @returns {boolean} result `true` - is open; `false` otherwise.
     */
    isOpen(): boolean;
    /**
     * Clears all the amqp sessions from the internal map maintained in rhea. This does not remove any
     * of the event handlers added in the rhea-promise library. To clear such event handlers, either
     * call remove() or close() on each session
     */
    removeAllSessions(): void;
    /**
     * Determines whether the remote end of the connection is open.
     * @returns {boolean} result `true` - is open; `false` otherwise.
     */
    isRemoteOpen(): boolean;
    /**
     * Gets the connection error if present.
     * @returns {ConnectionError | undefined} ConnectionError | undefined
     */
    getError(): ConnectionError | undefined;
    /**
     * Gets the peer certificate if present.
     * @returns {PeerCertificate | undefined} PeerCertificate | undefined
     */
    getPeerCertificate(): PeerCertificate | undefined;
    /**
     * Gets the tls socket if present.
     * @returns {Socket | undefined} Socket | undefined
     */
    getTlsSocket(): Socket | undefined;
    /**
     * Determines whether the close from the peer is a response to a locally initiated close request
     * for the connection.
     * @returns {boolean} `true` if close was locally initiated, `false` otherwise.
     */
    wasCloseInitiated(): boolean;
    /**
     * Creates an amqp session on the provided amqp connection.
     * @param options A set of options including a signal used to cancel the operation.
     * @return {Promise<Session>} Promise<Session>
     * - **Resolves** the promise with the Session object when rhea emits the "session_open" event.
     * - **Rejects** the promise with an AmqpError when rhea emits the "session_close" event while
     * trying to create an amqp session or with an AbortError if the operation was cancelled.
     */
    createSession(options?: SessionCreateOptions): Promise<Session>;
    /**
     * Creates an amqp sender link. It either uses the provided session or creates a new one.
     * - **Resolves** the promise with the Sender object when rhea emits the "sender_open" event.
     * - **Rejects** the promise with an AmqpError when rhea emits the "sender_close" event while
     * trying to create an amqp session or with an AbortError if the operation was cancelled.
     * @param {CreateSenderOptions} options Optional parameters to create a sender link.
     * @return {Promise<Sender>} Promise<Sender>.
     */
    createSender(options?: CreateSenderOptions): Promise<Sender>;
    /**
     * Creates an awaitable amqp sender. It either uses the provided session or creates a new one.
     * @param options Optional parameters to create an awaitable sender link.
     * - If `onError` and `onSessionError` handlers are not provided then the `AwaitableSender` will
     * clear the timer and reject the Promise for all the entries of inflight send operation in its
     * `deliveryDispositionMap`.
     * - If the user is handling the reconnection of sender link or the underlying connection in it's
     * app, then the `onError` and `onSessionError` handlers must be provided by the user and (s)he
     * shall be responsible of clearing the `deliveryDispositionMap` of inflight `send()` operation.
     *
     * @return Promise<AwaitableSender>.
     */
    createAwaitableSender(options?: CreateAwaitableSenderOptions): Promise<AwaitableSender>;
    /**
     * Creates an amqp receiver link. It either uses the provided session or creates a new one.
     * - **Resolves** the promise with the Sender object when rhea emits the "receiver_open" event.
     * - **Rejects** the promise with an AmqpError when rhea emits the "receiver_close" event while
     * trying to create an amqp session or with an AbortError if the operation was cancelled.
     * @param {CreateReceiverOptions} options Optional parameters to create a receiver link.
     * @return {Promise<Receiver>} Promise<Receiver>.
     */
    createReceiver(options?: CreateReceiverOptions): Promise<Receiver>;
    /**
     * Creates an amqp sender-receiver link. It either uses the provided session or creates a new one.
     * This method creates a sender-receiver link on the same session. It is useful for management
     * style operations where one may want to send a request and await for response.
     * @param {SenderOptions} senderOptions Parameters to create a sender.
     * @param {ReceiverOptions} receiverOptions Parameters to create a receiver.
     * @param {CreateRequestResponseLinkOptions} [options] Optional parameters to control how sender and receiver link creation.
     * @return {Promise<ReqResLink>} Promise<ReqResLink>
     */
    createRequestResponseLink(senderOptions: SenderOptions, receiverOptions: ReceiverOptions, options?: CreateRequestResponseLinkOptions): Promise<ReqResLink>;
    /**
     * Adds event listeners for the possible events that can occur on the connection object and
     * re-emits the same event back with the received arguments from rhea's event emitter.
     * @private
     * @returns {void} void
     */
    private _initializeEventListeners;
}
//# sourceMappingURL=connection.d.ts.map